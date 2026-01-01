<template>
  <!-- 页面容器 -->
  <div class="heritage-page">
    <!-- 内容区域容器 -->
    <div class="section-container">
      <!-- 筛选器容器 -->
      <div class="filter-container">
        <div class="category-filter">
          <!-- 分类按钮 -->
          <el-button @click="setActiveCategory('')"
                     class="category-btn"
                     :class="{ 'active': activeCategory === '' }"
          >
            全部类别
          </el-button>
          <!-- 动态生成分类按钮 -->
          <el-button
              v-for="category in onlyCategories"
              :key="category"
              class="category-btn"
              @click="setActiveCategory(category)"
              :class="{ 'active': activeCategory === category }"
          >
            {{ category }}
          </el-button>
        </div>
      </div>

      <!-- 遗产项目网格布局显示 -->
      <div class="heritage-grid">
        <div v-for="item in heritageList"
             :key="item.heritageId"
             class="heritage-card"
             @click="goToDetail(item.heritageId)"
        >
          <!-- 卡片图片区域 -->
          <div class="card-image">
            <!-- 非遗图片 -->
            <img :src="baseUrl + item.image" :alt="item.name">
            <div class="overlay">
              <div class="overlay-content">
                <span>查看详情</span>
              </div>
            </div>
          </div>
          <!-- 卡片内容区域 -->
          <div class="card-content">
            <h3 class="card-title">{{ item.title }}</h3>
            <p class="card-desc">{{ item.description }}</p>
            <div class="card-meta">
              <!-- 地理位置 -->
              <span class="card-location">
                                <el-icon><LocationFilled/></el-icon>
                                {{ item.location }}
                            </span>
              <!-- 项目类别 -->
              <span class="card-category">{{ item.category }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 分页组件 -->
      <pagination
          style="display: flex; justify-content: center"
          v-show="total>0"
          :total="total"
          v-model:page="query.pageNum"
          v-model:limit="query.pageSize"
          @pagination="getList"
          :pageSizes="[6,12,24,48]"
      />
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {listHeritage, selectOnlyCategories} from "@/api/ich/heritage.js";
import {LocationFilled} from "@element-plus/icons-vue";
import {useRouter} from "vue-router";

//当前激活的类别
const activeCategory = ref('')

//查询参数
const query = ref({
  pageNum: 1,
  pageSize: 6,
  category: null,
})

//基础URL地址
const baseUrl = import.meta.env.VITE_APP_BASE_API

//非遗项目列表数据
const heritageList = ref([])

//数据总数
const total = ref(0)

//计算属性
const onlyCategories = ref([])

//根据类别进行条件查询
const setActiveCategory = (category) => {
  activeCategory.value = category
  if (category === '') {
    query.value = {
      pageNum: 1,
      pageSize: 6,
      category: null,
    }
    getList()
  } else {
    query.value = {
      pageNum: 1,
      pageSize: 6,
      category: category,
    }
    getList()
  }
}

//查询数据
const getList = () => {
  listHeritage(query.value).then(res => {
    heritageList.value = res.rows
    total.value = res.total
  })
}
//路由实例
const router = useRouter()

//查看非遗项目详细
const goToDetail = (heritageId) => {
router.push('/index/heritageDetail/'+ heritageId)
}

//组件挂载后执行
onMounted(() => {
  getList()
  selectOnlyCategories().then(res => {
    onlyCategories.value = res.data
  })
})

</script>

<style scoped>
/* 页面容器样式 */
.heritage-page {
  width: 100%;
  background: #f8f9fa; /* 浅灰色背景 */
}

/* 内容区域容器 */
.section-container {
  max-width: 1300px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 0 20px 40px; /* 内边距：上 0，左右 20px，下 40px */
}

/* 筛选器容器样式 */
.filter-container {
  padding: 25px; /* 内边距 */
  background: #f8f9fa; /* 浅灰色背景 */
  border-bottom: 1px solid #e9ecef; /* 底部边框 */
}

/* 分类筛选器布局 */
.category-filter {
  display: flex; /* 弹性布局 */
  flex-wrap: wrap; /* 允许换行 */
  gap: 12px; /* 元素间距 */
  justify-content: center; /* 水平居中 */
}

/* 分类按钮基础样式 */
.category-btn {
  border-radius: 30px !important; /* 圆形边框 */
  border: 2px solid #e9ecef !important; /* 边框样式 */
  background: white !important; /* 白色背景 */
  color: #495057 !important; /* 文字颜色 */
  padding: 8px 20px !important; /* 内边距 */
  font-weight: 500 !important; /* 字体粗细 */
  transition: all 0.3s ease !important; /* 过渡动画 */
}

/* 分类按钮悬停效果 */
.category-btn:hover {
  border-color: #8B4513 !important; /* 边框颜色变为棕色 */
  color: #8B4513 !important; /* 文字颜色变为棕色 */
  transform: translateY(-2px) !important; /* 向上移动2px */
  box-shadow: 0 4px 12px rgba(142, 45, 226, 0.15) !important; /* 添加阴影 */
}

/* 激活状态的分类按钮 */
.category-btn.active {
  background: #8B4513 !important; /* 棕色背景 */
  border-color: #8B4513 !important; /* 棕色边框 */
  color: white !important; /* 白色文字 */
  box-shadow: 0 4px 15px rgba(142, 45, 226, 0.3) !important; /* 更明显的阴影 */
}

/* 遗产网格布局 */
.heritage-grid {
  display: grid; /* 网格布局 */
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr)); /* 自适应列数，最小320px */
  gap: 30px; /* 网格间距 */
  padding: 30px; /* 内边距 */
  background: #fff; /* 白色背景 */
}

/* 遗产卡片基础样式 */
.heritage-card {
  border-radius: 15px; /* 圆角边框 */
  overflow: hidden; /* 隐藏溢出内容 */
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.08); /* 卡片阴影 */
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.1); /* 缓动过渡动画 */
  cursor: pointer; /* 鼠标指针变为手型 */
  background: white; /* 白色背景 */
  position: relative; /* 相对定位 */
}

/* 卡片悬停效果 */
.heritage-card:hover {
  transform: translateY(-10px) scale(1.02); /* 向上移动并轻微放大 */
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.15); /* 增强阴影效果 */
}

/* 卡片悬停时显示遮罩层 */
.heritage-card:hover .overlay {
  opacity: 1; /* 完全显示遮罩层 */
}

/* 卡片悬停时图片放大效果 */
.heritage-card:hover .card-image img {
  transform: scale(1.1); /* 图片放大1.1倍 */
}

/* 卡片图片容器 */
.card-image {
  height: 220px; /* 固定高度 */
  overflow: hidden; /* 隐藏溢出内容 */
  position: relative; /* 相对定位 */
}

/* 卡片图片样式 */
.card-image img {
  width: 100%; /* 宽度100% */
  height: 100%; /* 高度100% */
  object-fit: cover; /* 覆盖整个容器，保持比例 */
  transition: transform 0.5s ease; /* 图片变换过渡动画 */
}

/* 遮罩层样式 */
.overlay {
  position: absolute; /* 绝对定位 */
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(226, 154, 45, 0.7); /* 半透明棕色背景 */
  display: flex; /* 弹性布局 */
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
  opacity: 0; /* 默认隐藏 */
  transition: opacity 0.4s ease; /* 透明度过渡动画 */
  z-index: 1; /* 层级在图片之上 */
}

/* 遮罩层内容样式 */
.overlay-content {
  color: white; /* 白色文字 */
  text-align: center; /* 文字居中 */
  font-size: 18px; /* 字体大小 */
  font-weight: 500; /* 字体粗细 */
}

/* 遮罩层图标样式 */
.overlay-content i {
  display: block; /* 块级显示 */
  font-size: 24px; /* 图标大小 */
  margin-bottom: 8px; /* 底部外边距 */
}

/* 卡片内容区域 */
.card-content {
  padding: 25px; /* 内边距 */
}

/* 卡片标题样式 */
.card-title {
  font-size: 20px; /* 字体大小 */
  font-weight: 700; /* 加粗 */
  margin: 0 0 12px 0; /* 外边距 */
  color: #212529; /* 深灰色文字 */
  line-height: 1.3; /* 行高 */
}

/* 卡片描述样式 */
.card-desc {
  color: #6c757d; /* 中灰色文字 */
  font-size: 15px; /* 字体大小 */
  line-height: 1.6; /* 行高 */
  margin-bottom: 20px; /* 底部外边距 */
  display: -webkit-box; /* 使用webkit盒子模型 */
  -webkit-line-clamp: 3; /* 限制显示3行 */
  -webkit-box-orient: vertical; /* 垂直方向 */
  overflow: hidden; /* 隐藏溢出内容 */
}

/* 卡片元信息布局 */
.card-meta {
  display: flex; /* 弹性布局 */
  justify-content: space-between; /* 两端对齐 */
  align-items: center; /* 垂直居中 */
}

/* 位置和分类标签基础样式 */
.card-location, .card-category {
  font-size: 14px; /* 字体大小 */
  padding: 6px 12px; /* 内边距 */
  border-radius: 20px; /* 圆形边框 */
  background-color: #f8f9fa; /* 浅灰色背景 */
  color: #495057; /* 文字颜色 */
  display: flex; /* 弹性布局 */
  align-items: center; /* 垂直居中 */
  gap: 5px; /* 元素间距 */
}

/* 位置图标颜色 */
.card-location i {
  color: #8B4513; /* 棕色图标 */
}

/* 分类标签特殊样式 */
.card-category {
  background: #d9a37d; /* 浅棕色背景 */
  color: #495057; /* 文字颜色 */
  font-weight: 500; /* 字体粗细 */
}

/* 特色卡片样式 */
.heritage-card.featured {
  border: 2px solid #ff6b6b; /* 红色边框 */
  position: relative; /* 相对定位 */
  overflow: visible; /* 显示溢出内容 */
}

/* 特色卡片装饰边框 */
.heritage-card.featured::before {
  content: ''; /* 伪元素内容 */
  position: absolute; /* 绝对定位 */
  top: -2px;
  left: -2px;
  right: -2px;
  bottom: -2px;
  background: #8B4513; /* 棕色背景 */
  z-index: -1; /* 层级在卡片之下 */
  border-radius: 15px; /* 圆角边框 */
}

.category-btn.active.el-button--primary {
  background: #8B4513 !important;
  border-color: #8B4513 !important;
}
</style>