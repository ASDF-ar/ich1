<template>
  <!-- 项目申报页面容器 -->
  <div class="projects-container">
    <div class="projects-content">
      <!-- 申报须知卡片 -->
      <el-card class="projects-card">
        <template #header>
          <div class="card-header">
            <span>申报须知</span>
          </div>
        </template>
        <!-- 申报须知内容 -->
        <div class="notice-content">
          <p>非物质文化遗产项目申报需要准备完整的材料，包括项目介绍、历史渊源、传承情况等内容。</p>
          <!-- 申报要求列表 -->
          <ul>
            <li>申报项目需具有鲜明的民族特色和历史文化价值</li>
            <li>需提供详细的传承谱系和传承人信息</li>
            <li>项目应具有活态传承的特点，至今仍有传承活动</li>
            <li>申报材料需真实、完整、准确</li>
          </ul>
          <p>申报成功后，项目将获得政府保护和传承支持。</p>
        </div>
      </el-card>

      <!-- 在线申报卡片 -->
      <el-card class="projects-card">
        <template #header>
          <div class="card-header">
            <span>在线申报</span>
          </div>
        </template>
        <el-form ref="formRef" :model="form" :rules="rules" label-width="120px" class="project-form">
          <el-form-item label="项目名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入非遗项目名称"/>
          </el-form-item>
          <el-form-item label="所属类别" prop="categoryId">
            <el-select v-model="form.categoryId" placeholder="请选择所属类别">
              <el-option
                  v-for="item in categoryList"
                  :key="item.pcId"
                  :label="item.name"
                  :value="item.pcId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="联系方式" prop="contact">
            <el-input v-model="form.contact" placeholder="请输入联系方式"/>
          </el-form-item>
          <el-form-item label="项目简介" prop="introduction">
            <el-input :rows="4" v-model="form.introduction" type="textarea"
                      placeholder="请简要介绍该项目的历史渊源、文化价格和传承现状"/>
          </el-form-item>
          <el-form-item label="详细描述" prop="description">
            <el-input :rows="6" v-model="form.description" type="textarea"
                      placeholder="请详细描述该项目的具体内容、表现形式、核心技艺等"/>
          </el-form-item>
          <el-form-item label="传承谱系" prop="inheritance">
            <el-input :rows="4" v-model="form.inheritance" type="textarea"
                      placeholder="请介绍该项目的传承历史和传承人谱系"/>
          </el-form-item>
          <el-form-item label="传承人信息" prop="inheritors">
            <el-input :rows="4" v-model="form.inheritors" type="textarea"
                      placeholder="请输入主要传承人信息（可多个）"/>
          </el-form-item>

          <el-form-item>
            <!-- 提交按钮 -->
            <el-button type="primary" @click="submitForm(formRef)">
              提交申报
            </el-button>
            <!-- 重置按钮 -->
            <el-button @click="resetForm(formRef)">
              重置
            </el-button>
          </el-form-item>
        </el-form>

      </el-card>

    </div>
  </div>
</template>

<script setup>
import {reactive, onMounted, ref} from 'vue'
import {selectAllCategoryList} from "@/api/ich/category.js";
import {addProject} from "@/api/ich/project.js";
import {ElMessage} from "element-plus";

//表单引用
const formRef = ref()

//表单项
const form = reactive({
  name: null, //项目名称
  categoryId: null, //项目申报类别ID
  introduction: null, //项目简介
  description: null, //详细描述
  inheritance: null, //传承谱系
  inheritors: null, //传承人
  contact: null, //联系方式
})

//表单验证
const rules = reactive({
  name: [
    {required: true, message: "项目名称不能为空", trigger: "blur"}
  ],
  categoryId: [
    {required: true, message: "请选择所属类别", trigger: "change"}
  ],
  introduction: [
    {required: true, message: "项目简介不能为空", trigger: "blur"}
  ],
  description: [
    {required: true, message: "详细描述不能为空", trigger: "blur"}
  ],
  inheritance: [
    {required: true, message: "传承谱系不能为空", trigger: "blur"}
  ],
  inheritors: [
    {required: true, message: "传承人信息不能为空", trigger: "blur"}
  ],
  contact: [
    {required: true, message: "联系方式不能为空", trigger: "blur"}
  ],
})

//提交申报
const submitForm = (formRef) => {
  //如果表单实例不存在, 直接返回
  if (!formRef) return
  //验证表单
  formRef.validate((valid) => {
    if (valid) {
      //表单验证通过后, 调用API提交数据
      addProject(form).then(res => {
        //显示成功提示
        ElMessage.success("申报信息提交成功!我们将尽快审核您的申请")
        //重置表单的所有字段
        formRef.resetFields()
      })
    }
  })
}

//重置表单
const resetForm = (formRef) => {
  //如果表单实例不存在, 直接返回
  if (!formRef) return
  //重置表单的所有字段
  formRef.resetFields()
}

const categoryList = ref([])

onMounted(() => {
  selectAllCategoryList().then(res => {
    categoryList.value = res.data
  })
})


</script>

<style scoped>
/* 项目申报容器样式 */
.projects-container {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 20px; /* 内边距 */
}

/* 主要内容区域样式 */
.projects-content {
  display: flex; /* 弹性布局 */
  flex-direction: column; /* 垂直方向排列 */
  gap: 20px; /* 子元素间距 */
}

/* 项目卡片通用样式 */
.projects-card {
  border-radius: 8px; /* 圆角边框 */
  /* 卡片阴影效果 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

/* 卡片头部样式 */
.card-header {
  font-size: 1.3rem; /* 字体大小 */
  font-weight: 600; /* 字体粗细 */
  color: #2c3e50; /* 字体颜色 */
}

/* 通知内容区域样式 */
.notice-content p {
  line-height: 1.8; /* 行高 */
  margin-bottom: 15px; /* 底部外边距 */
}

/* 通知内容无序列表样式 */
.notice-content ul {
  padding-left: 20px; /* 左内边距 */
  margin-bottom: 15px; /* 底部外边距 */
}

/* 通知内容列表项样式 */
.notice-content li {
  line-height: 1.8; /* 行高 */
  margin-bottom: 8px; /* 底部外边距 */
}

/* 项目表单样式 */
.project-form {
  padding: 20px 0; /* 上下内边距 */
}
</style>
