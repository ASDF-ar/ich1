<template>
    <section class="app-main">
        <router-view v-slot="{ Component, route }">
            <el-card class="app-content-card">
                <transition name="fade-transform" mode="out-in">
                    <div>
                        <keep-alive :include="tagsViewStore.cachedViews">
                            <component v-if="!route.meta.link" :is="Component" :key="route.path"/>
                        </keep-alive>
                    </div>
                </transition>
            </el-card>
        </router-view>
        <iframe-toggle/>
    </section>
</template>

<script setup>
import iframeToggle from "./IframeToggle/index"
import useTagsViewStore from '@/store/modules/tagsView'

const route = useRoute()
const tagsViewStore = useTagsViewStore()

onMounted(() => {
    addIframe()
})

watchEffect(() => {
    addIframe()
})

function addIframe() {
    if (route.meta.link) {
        useTagsViewStore().addIframeView(route)
    }
}
</script>

<style lang="scss" scoped>
.app-main {
  /* 50= navbar  50  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
  background-color: #f5f7f9;
  transition: all 0.3s ease;
  padding: 16px;
}

.fixed-header + .app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    min-height: calc(80vh);
  }

  .fixed-header + .app-main {
    padding-top: 84px;
  }
}

// 主内容区域圆角和阴影
.app-main {
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

// 内容卡片样式优化
.app-content-card {
  min-height: calc(100vh - 117px);
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;

  :deep(.el-card__body) {
    padding: 20px;
    height: 100%;
  }


}

// 添加淡入动画


</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 6px;
  }
}

::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-track {
  background-color: #f1f1f1;
  border-radius: 3px;
}

::-webkit-scrollbar-thumb {
  background-color: #c0c0c0;
  border-radius: 3px;
  transition: all 0.3s ease;

  &:hover {
    background-color: #a0a0a0;
  }
}
</style>
