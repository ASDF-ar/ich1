<template>
  <!-- 非遗详情页面容器 -->
  <div class="heritage-detail-page" v-loading="loading">
    <div class="detail-container">
      <!-- 返回按钮 -->
      <div class="back-button">
        <el-button @click="router.go(-1)" type="primary">
          <el-icon>
            <Back/>
          </el-icon>
          返回列表
        </el-button>
      </div>

      <!-- 标题区域 -->
      <div class="detail-header">
        <div class="heritage-title">{{ heritage.title }}</div>
        <div class="heritage-tags">
          <el-tag size="large"
                  effect="dark"
          >
            {{ heritage.category }}
          </el-tag>
        </div>
      </div>

      <!-- 主要信息区域 -->
      <div class="detail-content">
        <!-- 左侧图片展示区域 -->
        <div class="image-section">
          <div class="main-image">
            <img :src="baseUrl + heritage.image" alt="">
          </div>
        </div>

        <!-- 右侧信息区域 -->
        <div class="info-section">
          <div class="basic-info">
            <div class="info-item">
              <label>所在地: </label>
              <span>{{ heritage.location }}</span>
            </div>

            <div class="info-item">
              <label>传承状态: </label>
              <span v-if="heritage.status === '良好'" style="font-weight: bold;">
                                <el-tag effect="light" type="success" color="green"> {{ heritage.status }}</el-tag>
                            </span>
              <span v-if="heritage.status === '一般'" style="font-weight: bold;">
                                 <el-tag effect="light" type="warning" color="blue">{{ heritage.status }}</el-tag>
                            </span>
              <span v-if="heritage.status === '濒危'" style="font-weight: bold;">
                                <el-tag effect="light" type="danger" color="red">{{ heritage.status }}</el-tag>
                            </span>
            </div>
          </div>

          <!-- 项目简介 -->
          <div class="description-section">
            <h3>项目简介</h3>
            <p class="description-text">{{ heritage.description }}</p>
          </div>
        </div>
      </div>

      <!-- 详细介绍区域 -->
      <div class="detail-description">
        <h1>详细介绍</h1>
        <div class="rich-content" v-html="heritage.detailDescription"></div>
      </div>

    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {getHeritage} from "@/api/ich/heritage.js";
import {Back} from "@element-plus/icons-vue";

const route = useRoute()
const router = useRouter()

//非遗项目详情数据
const heritage = ref({})

//加载状态
const loading = ref(false)

//基础URL
const baseUrl = import.meta.env.VITE_APP_BASE_API

//组件挂载后执行
onMounted(() => {
  loading.value = true
  const heritageId = route.params.id
  getHeritage(heritageId).then(res => {
    heritage.value = res.data
    loading.value = false
  })
})

</script>

<style scoped>
/* 页面容器样式 */
.heritage-detail-page {
  width: 100%; /* 宽度100% */
  background: #f8f9fa; /* 浅灰色背景 */
  padding: 20px 0 40px; /* 上下内边距 */
}

/* 详情内容容器 */
.detail-container {
  max-width: 1500px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 0 20px; /* 左右内边距 */
}

/* 返回按钮区域 */
.back-button {
  margin-bottom: 20px; /* 底部外边距 */
}

/* 头部区域样式 */
.detail-header {
  background: white; /* 白色背景 */
  padding: 30px; /* 内边距 */
  border-radius: 15px; /* 圆角边框 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
  margin-bottom: 30px; /* 底部外边距 */
}

/* 标题样式 */
.heritage-title {
  font-size: 32px; /* 字体大小 */
  font-weight: bold; /* 粗体 */
  margin: 0 0 20px 0; /* 外边距，只设置底部 */
  color: #212529; /* 深灰色文字 */
}

/* 标签容器样式 */
.heritage-tags {
  display: flex; /* 弹性布局 */
  gap: 15px; /* 子元素间距 */
}

/* 主要内容区域网格布局 */
.detail-content {
  display: grid; /* 网格布局 */
  grid-template-columns: 1fr 1fr; /* 两列等宽 */
  gap: 30px; /* 列间距 */
  margin-bottom: 40px; /* 底部外边距 */
}

/* 图片区域样式 */
.image-section {
  background: white; /* 白色背景 */
  border-radius: 15px; /* 圆角边框 */
  overflow: hidden; /* 隐藏溢出内容 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
  padding: 20px; /* 内边距 */
}

/* 主图片容器 */
.main-image {
  width: 100%; /* 宽度100% */
  height: 400px; /* 固定高度 */
  border-radius: 10px; /* 圆角边框 */
  overflow: hidden; /* 隐藏溢出内容 */
}

/* 主图片样式 */
.main-image img {
  width: 100%; /* 宽度100% */
  height: 100%; /* 高度100% */
  object-fit: cover; /* 图片填充方式，保持比例覆盖 */
}

/* 信息区域样式 */
.info-section {
  background: white; /* 白色背景 */
  border-radius: 15px; /* 圆角边框 */
  overflow: hidden; /* 隐藏溢出内容 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
  padding: 30px; /* 内边距 */
}

/* 基本信息区域 */
.basic-info {
  margin-bottom: 30px; /* 底部外边距 */
}

/* 信息项样式 */
.info-item {
  display: flex; /* 弹性布局 */
  margin-bottom: 20px; /* 底部外边距 */
  padding-bottom: 20px; /* 底部内边距 */
  border-bottom: 1px solid #eee; /* 底部边框 */
}

/* 最后一个信息项移除边框 */
.info-item:last-child {
  margin-bottom: 0; /* 移除底部外边距 */
  padding-bottom: 0; /* 移除底部内边距 */
  border-bottom: none; /* 移除底部边框 */
}

/* 信息项标签样式 */
.info-item label {
  font-weight: bold; /* 粗体 */
  color: #495057; /* 中灰色文字 */
  width: 100px; /* 固定宽度 */
  flex-shrink: 0; /* 禁止收缩 */
}

/* 信息项内容样式 */
.info-item span {
  color: #6c757d; /* 浅灰色文字 */
}

/* 描述区域 */
.description-section {
  margin-bottom: 30px; /* 底部外边距 */
}

/* 描述标题样式 */
.description-section h3 {
  font-size: 20px; /* 字体大小 */
  margin: 0 0 15px 0; /* 外边距，只设置底部 */
  color: #212529; /* 深灰色文字 */
}

/* 描述文本样式 */
.description-text {
  color: #6c757d; /* 浅灰色文字 */
  line-height: 1.8; /* 行高 */
  font-size: 16px; /* 字体大小 */
}

/* 详细介绍区域样式 */
.detail-description {
  background: white; /* 白色背景 */
  border-radius: 15px; /* 圆角边框 */
  overflow: hidden; /* 隐藏溢出内容 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
  padding: 30px; /* 内边距 */
  margin-bottom: 40px; /* 底部外边距 */
}

/* 详细介绍标题样式 */
.detail-description h2 {
  font-size: 24px; /* 字体大小 */
  margin: 0 0 20px 0; /* 外边距，只设置底部 */
  color: #212529; /* 深灰色文字 */
  padding-bottom: 15px; /* 底部内边距 */
  border-bottom: 2px solid #f0f0f0; /* 底部边框 */
}

/* 富文本内容样式 */
.rich-content {
  color: #495057; /* 中灰色文字 */
  line-height: 1.8; /* 行高 */
}

/* 富文本段落样式 */
.rich-content p {
  margin-bottom: 20px; /* 底部外边距 */
  font-size: 16px; /* 字体大小 */
}

/* 加载容器样式 */
.loading-container {
  max-width: 1200px; /* 最大宽度 */
  margin: 0 auto; /* 水平居中 */
  padding: 30px 20px; /* 内边距 */
  background: white; /* 白色背景 */
  border-radius: 15px; /* 圆角边框 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
}

/* 未找到容器样式 */
.not-found {
  max-width: 1200px; /* 最大宽度 */
  margin: 0 auto; /* 水平居中 */
  padding: 60px 20px; /* 内边距 */
  background: white; /* 白色背景 */
  border-radius: 15px; /* 圆角边框 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
  text-align: center; /* 文字居中 */
}
</style>
