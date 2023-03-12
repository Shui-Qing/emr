<template>
  <el-container style="height: 700px; border: 1px solid #eee">
    <el-aside width="200px">
      <el-input v-model="filterText" placeholder="输入关键字进行过滤" clearable />
      <el-tree
        ref="tree"
        class="filter-tree"
        :data="data"
        :props="defaultProps"
        :filter-node-method="filterNode"
        @current-change="fetchDataById"
      />
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px;height: 40px">
      </el-header>
      <h2 style="text-align: center">{{ title }}</h2>
      <form-create
        :rule="rule"
        ref="formCreate"
        :option="option"
        style="padding: 20px"
        @submit="onSubmit"
      />
    </el-container>
  </el-container>
</template>
<script>
import api from '@/api/template'
import emr from '@/api/emr'

export default {
  data() {
    return {
      list: null,
      filterText: '',
      data: [],
      defaultProps: { label: 'label' },
      id: '',
      option: null,
      rule: [],
      title: ''
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
      api.findAll().then(response => {
        var list = []
        for (let i = 0; i < response.data.length; i++) {
          list.push({ id: response.data[i]['id'], label: response.data[i]['medicalRecordName'] })
        }
        this.data = list
        this.id = list[0]['id']
        this.title = list[0]['label']
        this.rule = JSON.parse(response.data[0].formProps)
        this.option = JSON.parse(response.data[0].formOptions)
      }).catch(response => {
        console.log(response)
      })
    },
    fetchDataById(obj, b, c) {
      this.id = obj.id
      this.title = obj.label
      api.getById(this.id).then(response => {
        var template = response.data
        // 给表单设计器赋值
        this.rule = JSON.parse(template.formProps)
        this.option = JSON.parse(template.formOptions)
      })
    },
    onSubmit(formData,fApi) {
      const DATA = {
        'templateId': this.id,
        'medicalRecordName': this.title
      }
      for (const key in formData) {
        DATA[key] = formData[key]
      }
      // 可读性开始
      // const formShowData = {}
      // this.rule.forEach(item => {
      //   for (const key in formData) {
      //     console.log(key)
      //     if (key === item.field) {
      //       formShowData[item.title] = formData[key]
      //     }
      //   }
      // })
      // 可读性结束
      console.log(DATA)
      emr.save(DATA).then(data => {
        if (data && data.code === 0) {
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 1500,
            onClose: () => {
              fApi.resetFields()
              // this.$router.push('/example/list')
            }
          })
        } else {
          this.$message.error(data.msg)
        }
      })
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
