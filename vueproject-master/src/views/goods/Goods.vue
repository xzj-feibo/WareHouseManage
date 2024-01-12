/**
 * 基础菜单 商品管理
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small" v-model="formInline.goodsName" placeholder="输入商品名称"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.goodsNo" placeholder="输入商品代码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <el-button size="small" type="primary" icon="el-icon-plus" @click="handleEdit()">添加</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" :data="listData" highlight-current-row v-loading="loading" border element-loading-text="拼命加载中" style="width: 100%;border-color: #3a8ee6">
      <el-table-column align="center" type="selection" width="60">
      </el-table-column>
      <el-table-column align="center" sortable prop="id" label="列表id" v-if="false" width="120" >
      </el-table-column>
      <el-table-column sortable prop="goodsName" label="商品名称" width="120">
      </el-table-column>
      <el-table-column sortable prop="goodsNo" label="商品代码" width="120">
      </el-table-column>
      <el-table-column sortable prop="price" label="商品价格(元)" width="120">
      </el-table-column>
      <el-table-column sortable prop="inventory" label="商品库存" width="120">
      </el-table-column>
      <el-table-column sortable prop="stemPlace" label="原产地" width="150">
      </el-table-column>
      <el-table-column sortable prop="createTime" label="创建时间" width="270">
        <template slot-scope="scope">
          <div>{{scope.row.editTime}}</div>
        </template>
      </el-table-column>
      <el-table-column sortable prop="createUserName" label="创建人" width="100">
      </el-table-column>
      <el-table-column sortable prop="editTime" label="修改时间" width="270">
        <template slot-scope="scope">
          <div>{{scope.row.editTime}}</div>
        </template>
      </el-table-column>
      <el-table-column sortable prop="editUserName" label="修改人" width="100">
      </el-table-column>
      <el-table-column align="center" label="操作" min-width="200">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="30%" @click="closeDialog">
      <el-form label-width="120px" :model="editForm" :rules="rules" ref="editForm">
        <el-form-item label="商品名称" prop="goodsName">
          <el-input size="small" v-model="editForm.goodsName" auto-complete="off" placeholder="请输入商品名称"></el-input>
        </el-form-item>
        <el-form-item label="商品代码" prop="goodsNo">
          <el-input size="small" v-model="editForm.goodsNo" auto-complete="off" placeholder="请输入商品代码"></el-input>
        </el-form-item>
        <el-form-item label="商品价格" prop="price">
          <el-input size="small" v-model="editForm.price" auto-complete="off" placeholder="请输入商品价格"></el-input>
        </el-form-item>
        <el-form-item label="商品库存" prop="price">
          <el-input size="small" v-model="editForm.inventory" auto-complete="off" placeholder="请输入商品数量"></el-input>
        </el-form-item>
        <el-form-item label="原产地" prop="stemPlace">
          <el-input size="small" v-model="editForm.stemPlace" auto-complete="off" placeholder="请输入原产地"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog">取消</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { GoodsList, GoodsSave, GoodsDelete } from '../../api/basisMG'
import Pagination from '../../components/Pagination'
export default {
  data() {
    return {
      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: '添加',
      editForm: {
        goodsId: '',
        goodsName: '',
        goodsNo: '',
        price: '',
        inventory:'',
        stemPlace:'',
        token: localStorage.getItem('logintoken')
      },
      // rules表单验证
      rules: {
        goodsName: [
          { required: true, message: '请输入商品名称', trigger: 'blur' }
        ],
        goodsNo: [{ required: true, message: '请输入商品代码', trigger: 'blur' }],
        price: [{required: true, message: '请输入商品价格', trigger: 'blur'}],
        inventory: [{required: true, message: '请输入商品数量',trigger: 'blur'}],
        stemPlace: [{required: true, message: '请输入商品原产地', trigger: 'blur'}]
      },
      formInline: {
        page: 1,
        limit: 10,
        varLable: '',
        varName: '',
        token: localStorage.getItem('logintoken')
      },
      // 删除商品
      seletedata: {
        ids: '',
        token: localStorage.getItem('logintoken')
      },
      userparm: [], //搜索权限
      listData: [], //用户数据
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 10,
        total: 10
      }
    }
  },
  // 注册组件
  components: {
    Pagination
  },
  /**
   * 数据发生改变
   */

  /**
   * 创建完毕
   */
  created() {
    this.getdata(this.formInline)
  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    // 获取公司列表
    getdata(parameter) {
      this.loading = true

      /***
       * 调用接口，注释上面模拟数据 取消下面注释
       */
      GoodsList(parameter)
        .then(res => {
          this.loading = false
          if (res.success == false) {
            this.$message({
              type: 'info',
              message: res.msg
            })
          } else {
            this.listData = res.data
            // 分页赋值
            this.pageparm.currentPage = this.formInline.page
            this.pageparm.pageSize = this.formInline.limit
            this.pageparm.total = res.count
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('菜单加载失败，请稍后再试！')
        })
    },
    // 分页插件事件
    callFather(parm) {
      this.formInline.page = parm.currentPage
      this.formInline.limit = parm.pageSize
      this.getdata(this.formInline)
    },
    // 搜索事件
    search() {
      this.getdata(this.formInline)
    },
    //显示编辑界面
    handleEdit: function(index, row) {
      this.editFormVisible = true
      if (row != undefined && row != 'undefined') {
        this.title = '修改'
        this.editForm.goodsId = row.id
        this.editForm.goodsName = row.goodsName
        this.editForm.goodsNo = row.goodsNo
        this.editForm.price = row.price
        this.editForm.inventory = row.inventory
        this.editForm.stemPlace = row.stemPlace
      } else {
        this.title = '添加'
        this.editForm.goodsId = ''
        this.editForm.goodsName = ''
        this.editForm.goodsNo = ''
        this.editForm.price = ''
        this.editForm.inventory = ''
        this.editForm.stemPlace = ''
      }
    },
    // 编辑、增加页面保存方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          GoodsSave(this.editForm)
            .then(res => {
              this.editFormVisible = false
              this.loading = false
              if (res.success) {
                this.getdata(this.formInline)
                this.$message({
                  type: 'success',
                  message: '商品保存成功！'
                })
              } else {
                this.$message({
                  type: 'info',
                  message: res.msg
                })
              }
            })
            .catch(err => {
              this.editFormVisible = false
              this.loading = false
              this.$message.error('商品保存失败，请稍后再试！')
            })
        } else {
          return false
        }
      })
    },
    // 删除商品
    deleteUser(index, row) {
      this.$confirm('确定要删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          GoodsDelete(row.id)
            .then(res => {
              if (res.success) {
                this.$message({
                  type: 'success',
                  message: '商品已删除!'
                })
                this.getdata(this.formInline)
              } else {
                this.$message({
                  type: 'info',
                  message: res.msg
                })
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('商品删除失败，请稍后再试！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    // 关闭编辑、增加弹出框
    closeDialog() {
      this.editFormVisible = false
    }
  }
}
</script>

<style scoped>
.user-search {
  margin-top: 20px;
}
.userRole {
  width: 100%;
}
</style>


