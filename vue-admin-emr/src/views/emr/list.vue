<template>
  <div class="app-container">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable />
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button type="success" @click="addHandle()">新增</el-button>
        <el-button type="danger" :disabled="dataListSelections.length <= 0" @click="deleteHandle()">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="dataListLoading"
      :data="dataList"
      border
      height="570"
      style="width: 100%;"
      @selection-change="selectionChangeHandle"
    >
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50"
      />
      <el-table-column
        label="序号"
        width="70"
        align="center"
      >
        <template slot-scope="scope">
          {{ (pageIndex - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="编码"
      />
      <el-table-column
        prop="medicalRecordName"
        header-align="center"
        align="center"
        label="病历名称"
      />
      <el-table-column
        header-align="center"
        align="center"
        width="100"
        label="模板"
      >
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="show(scope.row)">查看</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="description"
        header-align="center"
        align="center"
        label="描述"
      />
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="状态"
      >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            :disabled="scope.row.status==1"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="publish(scope.row.id)"
          />
        </template>
      </el-table-column>
      <el-table-column
        prop="updateTime"
        header-align="center"
        align="center"
        label="更新时间"
        width="180"
      />
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="创建时间"
        width="180"
      />
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作"
      >
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" title="修改" @click="updateHandle(scope.row.id)" />
          <el-button type="danger" icon="el-icon-delete" size="mini" title="删除" @click="deleteHandle(scope.row.id)" />
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
    />
    <el-dialog title="查看审批设置" :visible.sync="formDialogVisible" width="70%">
      <h3>基本信息</h3>
      <el-divider />
      <el-form ref="flashPromotionForm" label-width="150px" size="small" style="padding-right: 40px;">
        <el-form-item label="病历名称" style="margin-bottom: 0px;">{{ template.medicalRecordName }}</el-form-item>
        <el-form-item label="最近修改时间" style="margin-bottom: 0px;">{{ template.updateTime }}</el-form-item>
      </el-form>
      <h3>表单信息</h3>
      <el-divider />
      <div>
        <form-create
          :rule="rule"
          :option="option"
        />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="formDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import api from '@/api/template'
export default {
  data() {
    return {
      dataForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      rule: [],
      option: {},
      template: {},
      formDialogVisible: false
    }
  },
  created() {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      api.getDataList(this.pageIndex, this.pageSize, this.dataForm.key).then(data => {
        console.log(data)
        if (data && data.code === 0) {
          this.dataList = data.data.content
          this.totalPage = data.data.totalElements
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val
    },
    // 新增 / 修改
    addHandle() {
      this.$router.push('/example/emr')
    },
    updateHandle(id) {
      this.$router.push('/example/emr?id=' + id)
    },
    // 删除
    deleteHandle(id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(ids)
        api.delete(ids).then(data => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    show(row) {
      this.rule = JSON.parse(row.formProps)
      this.option = JSON.parse(row.formOptions)
      this.template = row
      this.formDialogVisible = true
    },
    publish(id) {
      // api.publish(id).then(data => {
      //   if (data && data.code === 0) {
      //     this.$message({
      //       message: '发布成功',
      //       type: 'success',
      //       duration: 1000,
      //       onClose: () => {
      //         this.getDataList()
      //       }
      //     })
      //   } else {
      //     this.$message.error(data.msg)
      //   }
      // })
    }
  }
}
</script>
