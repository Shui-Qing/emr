<template>
  <div class="app-container">
    <el-steps :active="stepIndex" finish-status="success">
      <el-step title="基本设置" />
      <el-step title="表单设置" />
    </el-steps>

    <div class="tools-div">
      <el-button v-if="stepIndex > 1" icon="el-icon-check" type="primary" size="small" round @click="pre()">上一步
      </el-button>
      <el-button icon="el-icon-check" type="primary" size="small" round @click="next()">{{
        stepIndex == 2 ? '提交保存' : '下一步'
      }}
      </el-button>
      <el-button type="primary" size="small" @click="back()">返回</el-button>
    </div>

    <!-- 第一步 -->
    <div v-show="stepIndex == 1" style="margin-top: 20px;">
      <el-form ref="flashPromotionForm" label-width="150px" size="small" style="padding-right: 40px;">
        <el-form-item label="病历名称">
          <el-input v-model="Template.medicalRecordName" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="Template.description" />
        </el-form-item>
      </el-form>
    </div>

    <!-- 第二步 -->
    <div v-show="stepIndex == 2" style="margin-top: 20px;height: 700px">
      <!--表单构建器-->
      <fc-designer ref="designer" class="form-build" />
    </div>
  </div>
</template>

<script>
import api from '@/api/template'
export default {
  data() {
    return {
      stepIndex: 1,
      processTypeList: [],
      Template: {
        id: '',
        medicalRecordName: '',
        formProps: '',
        formOptions: '',
        description: '',
        isDeleted: 0,
        status: 0
      }
    }
  },

  created() {
    const id = this.$route.query.id
    if (id != null && id != '' && id != undefined) {
      this.fetchDataById(id)
    }
  },

  methods: {
    pre() {
      this.stepIndex -= 1
    },
    next() {
      if (this.stepIndex === 2) {
        this.Template.formProps = JSON.stringify(this.$refs.designer.getRule())
        this.Template.formOptions = JSON.stringify(this.$refs.designer.getOption())
        // console.log(JSON.stringify(this.Template))
        this.saveOrUpdate()
      }
      if (this.stepIndex === 1) {
        if (this.Template.id != null && this.Template.id != '' && this.Template.id != undefined) {
          this.$refs.designer.setRule(JSON.parse(this.Template.formProps))
          this.$refs.designer.setOption(JSON.parse(this.Template.formOptions))
        }
        this.stepIndex += 1
      }
    },

    fetchDataById(id) {
      api.getById(id).then(response => {
        this.Template = response.data
        // 给表单设计器赋值
        this.$refs.designer.setRule(JSON.parse(this.Template.formProps))
        this.$refs.designer.setOption(JSON.parse(this.Template.formOptions))
      })
    },

    saveOrUpdate() {
      console.log(this.Template)
      api.save(this.Template).then(data => {
        if (data && data.code === 0) {
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 1500,
            onClose: () => {
              this.$router.push('/example/list')
            }
          })
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    back() {
      this.$router.push('/example/list')
    }
  }
}
</script>
