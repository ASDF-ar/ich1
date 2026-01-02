<template>
  <!-- 传承人页面主容器 -->
  <div class="masters-page">
    <div class="section-container">
      <div class="masters-grid">
        <div v-for="master in masterList"
             :key="master.masterId"
             class="master-card"
             @click=""
        >
          <!-- 传承人照片 -->
          <div class="master-image">
            <img :src="baseUrl + master.image" alt="">
          </div>
          <!-- 传承人信息 -->
          <div class="master-info">
            <h3 class="master-name">{{ master.name }}</h3>
            <p class="master-skills">{{ master.skills }}</p>
            <div class="master-location">
              <el-icon>
                <Location/>
              </el-icon>
              {{ master.location }}
            </div>
          </div>
        </div>
      </div>

      <!-- 分页组件容器 -->
      <div class="pagination-container">
        <pagination
            v-show="total>0"
            :total="total"
            v-model:page="query.pageNum"
            v-model:limit="query.pageSize"
            @pagination="getList"
            :pageSizes="[6,12,24,48]"
        />
      </div>

    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue'
import {listMaster} from "@/api/ich/master.js";
import {Location} from "@element-plus/icons-vue";

//基础URL地址
const baseUrl = import.meta.env.VITE_APP_BASE_API

//查询参数  一页查六条 总共12条
const query = ref({
  pageNum: 1,
  pageSize: 6,
})

//传承人列表数据
const masterList = ref([])

//数据总数
const total = ref(0)

//查询数据
const getList = () => {
  listMaster(query.value).then(res => {
    masterList.value = res.rows
    total.value = res.total
  })
}

onMounted(() => {
  getList()
})

</script>

<style scoped>
/* 主页面容器样式 */
.masters-page {
  width: 100%; /* 宽度100% */
}

/* 内容区域容器样式 */
.section-container {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 18px 20px; /* 内边距 */
}

/* 深度选择器修改Element Plus输入框样式 */
:deep(.el-input__wrapper) {
  border-radius: 25px; /* 圆角边框 */
}

/* 传承人网格布局样式 */
.masters-grid {
  display: grid; /* 使用网格布局 */
  /* 自动填充列，最小280px，最大1fr */
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 30px; /* 网格间距 */
  margin-bottom: 40px; /* 底部外边距 */
}

/* 传承人卡片样式 */
.master-card {
  border-radius: 15px; /* 圆角 */
  overflow: hidden; /* 隐藏溢出内容 */
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.08); /* 阴影效果 */
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.1); /* 过渡动画 */
  cursor: pointer; /* 鼠标手型 */
  background: white; /* 背景色 */
  text-align: center; /* 文字居中 */
}

/* 卡片悬停效果 */
.master-card:hover {
  transform: translateY(-10px) scale(1.02); /* 上移和轻微放大 */
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.15); /* 增强阴影 */
}

/* 传承人图片容器样式 */
.master-image {
  height: 200px; /* 固定高度 */
  overflow: hidden; /* 隐藏溢出 */
}

/* 传承人图片样式 */
.master-image img {
  width: 100%; /* 宽度100% */
  height: 100%; /* 高度100% */
  object-fit: cover; /* 覆盖填充 */
  transition: transform 0.5s ease; /* 变换过渡 */
}

/* 传承人信息区域样式 */
.master-info {
  padding: 25px; /* 内边距 */
}

/* 传承人姓名样式 */
.master-name {
  font-size: 20px; /* 字体大小 */
  font-weight: 700; /* 字体粗细 */
  margin: 0 0 12px 0; /* 外边距 */
  color: #212529; /* 字体颜色 */
  line-height: 1.3; /* 行高 */
}

/* 传承人技能样式 */
.master-skills {
  color: #6c757d; /* 字体颜色 */
  font-size: 14px; /* 字体大小 */
  line-height: 1.5; /* 行高 */
  margin-bottom: 1px; /* 底部外边距 */
  min-height: 42px; /* 最小高度 */
  /* 多行文本溢出显示省略号 */
  display: -webkit-box;
  -webkit-line-clamp: 2; /* 显示2行 */
  -webkit-box-orient: vertical;
  overflow: hidden;
}

/* 传承人所在地样式 */
.master-location {
  display: flex; /* 弹性布局 */
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
  color: #6c757d; /* 字体颜色 */
  font-size: 14px; /* 字体大小 */
}

/* 所在地图标样式 */
.master-location i {
  margin-right: 5px; /* 右边距 */
  color: #8B4513; /* 图标颜色 -  saddlebrown */
}

/* 分页容器样式 */
.pagination-container {
  display: flex; /* 弹性布局 */
  justify-content: center; /* 水平居中 */
  margin-top: 1px; /* 顶部外边距 */
}

/* 深度选择器修改分页组件样式 */
:deep(.el-pagination) .btn-prev,
:deep(.el-pagination) .btn-next,
:deep(.el-pagination) .el-pager li {
  border-radius: 50%; /* 圆形按钮 */
  margin: 0 5px; /* 左右外边距 */
  min-width: 36px; /* 最小宽度 */
  height: 36px; /* 高度 */
  line-height: 36px; /* 行高 */
}

/* 激活页码样式 */
:deep(.el-pagination) .el-pager li.is-active {
  background: #8B4513; /* 背景色 - saddlebrown */
  color: white; /* 字体颜色 */
}
</style>