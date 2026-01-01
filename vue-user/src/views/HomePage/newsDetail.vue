<template>
  <!-- 新闻详情页面容器 -->
  <div class="news-detail-page" v-loading="loading">
    <div class="detail-container">
      <!-- 返回按钮 -->
      <div class="back-button">
        <el-button @click="router.go(-1)" type="primary" plain>
          <el-icon><ArrowLeft/></el-icon>
          返回新闻列表
        </el-button>
      </div>

      <!-- 新闻头部信息 -->
      <div class="detail-header">
        <h1 class="news-title">{{ news.title }}</h1>
        <div class="news-meta">
          <span>{{ news.createTime }}</span>
          <span>{{ news.author }}</span>
          <span class="news-category">{{ news.category }}</span>
        </div>
      </div>

      <!-- 新闻图片 -->
      <div class="cover-image">
        <img :src="baseUrl + news.image" alt="">
      </div>

      <!-- 新闻正文 -->
      <div class="detail-content">
        <div class="news-content" v-html="news.content"/>
      </div>

    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {useRoute, useRouter} from "vue-router";
import {ArrowLeft} from "@element-plus/icons-vue";
import {getNews} from "@/api/ich/news.js";

const router = useRouter()
const route = useRoute()

const baseUrl = import.meta.env.VITE_APP_BASE_API

const news = ref({})

//加载状态
const loading = ref(false)

//组件挂载完成后执行
onMounted(() => {
  loading.value = true
  //调用API获取新闻详情
  getNews(route.params.id).then(res => {
    news.value = res.data
    loading.value = false
  })
})

</script>

<style scoped>
/* 新闻详情页整体样式 */
.news-detail-page {
  width: 100%; /* 宽度100% */
  background: #f8f9fa; /* 浅灰色背景 */
  padding: 20px 0 40px; /* 上下内边距，底部更多 */
}

/* 主要内容容器 */
.detail-container {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 0 20px; /* 左右内边距 */
}

/* 返回按钮区域 */
.back-button {
  margin-bottom: 20px; /* 底部外边距 */
}

/* 新闻头部区域样式 */
.detail-header {
  background: white; /* 白色背景 */
  padding: 30px; /* 内边距 */
  border-radius: 15px; /* 圆角边框 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
  margin-bottom: 30px; /* 底部外边距 */
}

/* 新闻标题样式 */
.news-title {
  font-size: 32px; /* 字体大小 */
  font-weight: bold; /* 粗体 */
  margin: 0 0 20px 0; /* 底部外边距 */
  color: #212529; /* 深灰色文字 */
}

/* 新闻元信息容器 */
.news-meta {
  display: flex; /* 弹性布局 */
  gap: 20px; /* 子元素间距 */
  font-size: 14px; /* 字体大小 */
  color: #6c757d; /* 中灰色文字 */
}

/* 新闻分类标签样式 */
.news-category {
  background: #e6f7ff; /* 浅蓝色背景 */
  color: #3a7bd5; /* 蓝色文字 */
  padding: 2px 10px; /* 内边距 */
  border-radius: 4px; /* 小圆角 */
}

/* 封面图片容器 */
.cover-image {
  width: 100%; /* 宽度100% */
  height: 400px; /* 固定高度 */
  border-radius: 15px; /* 圆角边框 */
  overflow: hidden; /* 隐藏溢出内容 */
  margin-bottom: 30px; /* 底部外边距 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
}

/* 封面图片样式 */
.cover-image img {
  width: 100%; /* 宽度100% */
  height: 100%; /* 高度100% */
  object-fit: cover; /* 覆盖填充，保持比例 */
}

/* 正文内容区域 */
.detail-content {
  background: white; /* 白色背景 */
  border-radius: 15px; /* 圆角边框 */
  overflow: hidden; /* 隐藏溢出内容 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
  padding: 30px; /* 内边距 */
  margin-bottom: 40px; /* 底部外边距 */
}

/* 新闻正文内容样式 */
.news-content {
  color: #495057; /* 深灰色文字 */
  line-height: 1.8; /* 行高 */
}

/* 段落样式 */
.news-content p {
  margin-bottom: 20px; /* 底部外边距 */
  font-size: 16px; /* 字体大小 */
}

/* 加载状态容器 */
.loading-container {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 30px 20px; /* 内边距 */
  background: white; /* 白色背景 */
  border-radius: 15px; /* 圆角边框 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
}

/* 未找到容器 */
.not-found {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 60px 20px; /* 内边距 */
  background: white; /* 白色背景 */
  border-radius: 15px; /* 圆角边框 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
  text-align: center; /* 文字居中 */
}
</style>
