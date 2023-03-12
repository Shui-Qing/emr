<template>
  <el-container style="height: 700px; border: 1px solid #eee">
    <el-aside width="200px">
      <el-input v-model="filterText" placeholder="输入关键字进行过滤" clearable />
      <el-tree
        ref="tree"
        class="filter-tree"
        :data="data"
        accordion
        :filter-node-method="filterNode"
        @node-click="fetchDataById"
      />
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px;height: 40px" >
        <el-switch
          v-model="enableEdit"
          active-color="#ff4949"
          inactive-color="#13ce66"
          active-text="只读"
          @change="changeStatus"
          inactive-text="编辑">
        </el-switch>
      </el-header>
      <h2 style="text-align: center">{{ title }}</h2>
      <form-create
        v-model="fApi"
        :rule="rule"
        :option="option"
        style="padding: 20px"
        :value.sync="value"
      />
    </el-container>
  </el-container>
</template>
<script>
import templateApi from '@/api/template'
import api from '@/api/emr'

export default {
  data() {
    return {
      fApi: {},
      list: null,
      filterText: '',
      data: [],
      emrId: '',
      templateId: '',
      option: null,
      rule: [],
      title: '',
      value: null,
      enableEdit: true
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    this.findAll()
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    findAll() {
      api.getTree().then(response => {
        this.data = response.data
      }).catch(response => {
        console.log(response)
      })
    },
    fetchDataById(obj, b, c) {
      if (obj.templateId != null) {
        this.emrId = obj.id
        this.title = obj.label
        this.templateId = obj.templateId

        templateApi.getById(this.templateId).then(response => {
          var template = response.data
          // 给表单设计器赋值
          this.rule = JSON.parse(template.formProps)
          this.option = JSON.parse(template.formOptions)

          api.getById(this.emrId).then(response => {
            this.value = response.data
            this.fApi.disabled(this.enableEdit)
            this.fApi.resetBtn.show(!this.enableEdit) // 设置重置按钮显示状态
            this.fApi.btn.show(!this.enableEdit) // 设置提交按钮显示状态
          })
        })
      }
    },
    changeStatus(status) {
      this.enableEdit = status
      this.fApi.disabled(this.enableEdit)
      this.fApi.resetBtn.show(!this.enableEdit) // 设置重置按钮显示状态
      this.fApi.btn.show(!this.enableEdit) // 设置提交按钮显示状态
    }
  }
}
</script>

<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 40px;
  }

  .el-aside {
    color: #333;
  }
</style>
