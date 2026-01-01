<template>
  <!-- 活动详情页面容器 -->
  <div class="event-detail-page" v-loading="loading">
    <!-- 详情内容容器 -->
    <div class="detail-container">
      <!-- 返回按钮 -->
      <div class="back-button">
        <el-button @click="router.go(-1)" type="primary" plain>
          <el-icon><ArrowLeft/></el-icon>
          返回活动列表
        </el-button>
      </div>

      <!-- 标题区域 -->
      <div class="detail-header">
        <h1 class="event-title">{{ event.title }}</h1>
        <div class="event-meta">
          <span class="event-location">{{ event.location }}</span>
        </div>
      </div>

      <!-- 活动详细信息 -->
      <div class="event-info-card">
        <div class="info-item">
          <div class="info-label">活动时间</div>
          <div class="info-value">{{ event.date }} {{ event.time }}</div>
        </div>
        <div class="info-item">
          <div class="info-label">活动地点</div>
          <div class="info-value">{{ event.location }}</div>
        </div>
        <div class="info-item">
          <div class="info-label">主办单位</div>
          <div class="info-value">{{ event.organizer }}</div>
        </div>
        <div class="info-item">
          <div class="info-label">联系方式</div>
          <div class="info-value">{{ event.contact }}</div>
        </div>
      </div>

      <!-- 活动正文 -->
      <div class="detail-content">
        <h1 class="content-title">活动详情</h1>
        <div v-html="event.content" class="event-content"/>
      </div>

    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {useRoute, useRouter} from "vue-router";
import {getEvent} from "@/api/ich/event.js";
import {ArrowLeft} from "@element-plus/icons-vue";

const route = useRoute()

const router = useRouter()

//活动预告数据
const event = ref({})

//加载状态
const loading = ref(false)

//组件挂载后执行
onMounted(() => {
  loading.value = true
  getEvent(route.params.id).then(res => {
    event.value = res.data
    loading.value = false
  })
})

</script>

<style scoped>
/* 页面容器样式 */
.event-detail-page {
  width: 100%; /* 宽度100% */
  background: #f8f9fa; /* 浅灰色背景 */
  padding: 20px 0 40px; /* 上下内边距 */
}

/* 详情内容容器样式 */
.detail-container {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 0 20px; /* 左右内边距 */
}

/* 返回按钮区域样式 */
.back-button {
  margin-bottom: 20px; /* 底部外边距 */
}

/* 标题区域样式 */
.detail-header {
  background: white; /* 白色背景 */
  padding: 30px; /* 内边距 */
  border-radius: 15px; /* 圆角边框 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
  margin-bottom: 30px; /* 底部外边距 */
  text-align: center; /* 文本居中 */
}

/* 活动标题样式 */
.event-title {
  font-size: 32px; /* 字体大小 */
  font-weight: bold; /* 字体粗细 */
  margin: 0 0 20px 0; /* 外边距 */
  color: #212529; /* 字体颜色 */
}

/* 活动元信息容器样式 */
.event-meta {
  display: flex; /* 弹性布局 */
  justify-content: center; /* 水平居中 */
  gap: 30px; /* 子元素间距 */
  font-size: 16px; /* 字体大小 */
  color: #6c757d; /* 字体颜色 */
}

/* 活动地点样式 */
.event-location {
  color: #3a7bd5; /* 蓝色字体 */
  font-weight: 500; /* 中等字体粗细 */
}

/* 活动信息卡片样式 */
.event-info-card {
  background: linear-gradient(135deg, #3a7bd5, #00d2ff); /* 渐变背景 */
  border-radius: 15px; /* 圆角边框 */
  padding: 30px; /* 内边距 */
  margin-bottom: 30px; /* 底部外边距 */
  color: white; /* 白色字体 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15); /* 阴影效果 */
}

/* 信息项样式 */
.info-item {
  display: flex; /* 弹性布局 */
  margin-bottom: 20px; /* 底部外边距 */
}

/* 最后一个信息项取消底部外边距 */
.info-item:last-child {
  margin-bottom: 0;
}

/* 信息标签样式 */
.info-label {
  width: 100px; /* 固定宽度 */
  font-weight: bold; /* 字体粗细 */
  flex-shrink: 0; /* 不允许收缩 */
}

/* 信息值样式 */
.info-value {
  flex: 1; /* 占据剩余空间 */
}

/* 详情内容区域样式 */
.detail-content {
  background: white; /* 白色背景 */
  border-radius: 15px; /* 圆角边框 */
  overflow: hidden; /* 隐藏溢出内容 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
  padding: 30px; /* 内边距 */
  margin-bottom: 40px; /* 底部外边距 */
}

/* 内容标题样式 */
.content-title {
  font-size: 24px; /* 字体大小 */
  font-weight: bold; /* 字体粗细 */
  margin: 0 0 20px 0; /* 外边距 */
  color: #212529; /* 字体颜色 */
  padding-bottom: 10px; /* 底部内边距 */
  border-bottom: 2px solid #e9ecef; /* 底部边框 */
}

/* 活动内容样式 */
.event-content {
  color: #495057; /* 字体颜色 */
  line-height: 1.8; /* 行高 */
}

/* 活动内容中的三级标题样式 */
.event-content h3 {
  font-size: 18px; /* 字体大小 */
  margin: 20px 0 10px; /* 外边距 */
  color: #212529; /* 字体颜色 */
}

/* 活动内容中的段落样式 */
.event-content p {
  margin-bottom: 15px; /* 底部外边距 */
  font-size: 16px; /* 字体大小 */
}

/* 活动内容中的无序列表样式 */
.event-content ul {
  margin: 15px 0; /* 上下外边距 */
  padding-left: 20px; /* 左内边距 */
}

/* 活动内容中的有序列表样式 */
.event-content ol {
  margin: 15px 0; /* 上下外边距 */
  padding-left: 20px; /* 左内边距 */
}

/* 活动内容中无序列表项样式 */
.event-content ul li {
  margin-bottom: 8px; /* 底部外边距 */
}

/* 活动内容中有序列表项样式 */
.event-content ol li {
  margin-bottom: 8px; /* 底部外边距 */
}

/* 加载状态容器样式 */
.loading-container {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 30px 20px; /* 内边距 */
  background: white; /* 白色背景 */
  border-radius: 15px; /* 圆角边框 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08); /* 阴影效果 */
}
</style>
