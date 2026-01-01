<template>
  <div :class="{ 'has-logo': showLogo && showLogoInSidebar }" class="sidebar-container">
    <logo v-if="showLogo && showLogoInSidebar" :collapse="isCollapse" />
    <el-scrollbar wrap-class="scrollbar-wrapper" style="height: 100%">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="getMenuBackground"
        :text-color="getMenuTextColor"
        :unique-opened="true"
        :active-text-color="theme"
        :collapse-transition="false"
        mode="vertical"
        class="sidebar-menu"
      >
        <sidebar-item
          v-for="(route, index) in sidebarRouters"
          :key="route.path + index"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup>
import Logo from './Logo'
import SidebarItem from './SidebarItem'
import variables from '@/assets/styles/variables.module.scss'
import useAppStore from '@/store/modules/app'
import useSettingsStore from '@/store/modules/settings'
import usePermissionStore from '@/store/modules/permission'

const route = useRoute()
const appStore = useAppStore()
const settingsStore = useSettingsStore()
const permissionStore = usePermissionStore()

const sidebarRouters = computed(() => permissionStore.sidebarRouters)
const showLogo = computed(() => settingsStore.sidebarLogo)
const showLogoInSidebar = false // 不在侧边栏显示logo，因为已移到顶部导航栏
const sideTheme = computed(() => settingsStore.sideTheme)
const theme = computed(() => settingsStore.theme)
const isCollapse = computed(() => !appStore.sidebar.opened)

// 获取菜单背景色
const getMenuBackground = computed(() => {
  if (settingsStore.isDark) {
    return 'var(--sidebar-bg)'
  }
  return sideTheme.value === 'theme-dark' ? variables.menuBg : variables.menuLightBg
})

// 获取菜单文字颜色
const getMenuTextColor = computed(() => {
  if (settingsStore.isDark) {
    return 'var(--sidebar-text)'
  }
  return sideTheme.value === 'theme-dark' ? variables.menuText : variables.menuLightText
})

const activeMenu = computed(() => {
  const { meta, path } = route
  if (meta.activeMenu) {
    return meta.activeMenu
  }
  return path
})
</script>

<style lang="scss" scoped>
.sidebar-container {
  background-color: v-bind(getMenuBackground);
  transition: all 0.3s ease;

  .scrollbar-wrapper {
    background-color: v-bind(getMenuBackground);
  }

  .sidebar-menu {
    border: none;
    height: 100%;
    width: 100% !important;
    padding: 8px 0;

    :deep(.el-menu-item), :deep(.el-sub-menu__title) {
      margin: 2px 10px;
      border-radius: 8px;
      transition: all 0.3s ease;

      &:hover {
        background-color: rgba(255, 255, 255, 0.1) !important;
        transform: translateY(-2px);
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
      }

      .svg-icon {
        margin-right: 5px;
        margin-left: -5px;
        font-size: 25px;
        vertical-align: middle;
        transition: all 0.3s ease;
      }

      span {
        font-size: 14px;
        vertical-align: middle;
        transition: all 0.1s ease;
      }
    }

    :deep(.el-menu-item) {
      color: v-bind(getMenuTextColor);
      height: 55px;
      line-height: 55px;

      &.is-active {
        background: linear-gradient(90deg, var(--el-color-primary), var(--el-color-primary-light-3)) !important;
        color: #fff !important;
        position: relative;
        box-shadow: 0 4px 6px rgba(79, 132, 243, 0.3);
        border-radius: 8px;

        &::before {
          content: "";
          position: absolute;
          left: 0;
          top: 0;
          height: 100%;
          width: 4px;
          background-color: #ffffff80;
          border-radius: 0 4px 4px 0;
        }

        &:hover {
          background: linear-gradient(90deg, var(--el-color-primary), var(--el-color-primary-light-3)) !important;
          box-shadow: 0 4px 8px rgba(79, 132, 243, 0.4);
        }

        .svg-icon {
          transform: scale(1.1);
        }
      }
    }

    :deep(.el-sub-menu__title) {
      color: v-bind(getMenuTextColor);
      height: 55px;
      line-height: 55px;

      .svg-icon {
        margin-right: 5px;
        margin-left: -5px;
      }
    }

    :deep(.el-sub-menu) {
      &.is-active {
        > .el-sub-menu__title {
          background-color: rgba(255, 255, 255, 0.05);
          color: var(--el-color-primary) !important;
          font-weight: 500;
          position: relative;
          border-radius: 8px;

          &::before {
            content: "";
            position: absolute;
            left: 0;
            top: 0;
            height: 100%;
            width: 3px;
            background-color: var(--el-color-primary);
            border-radius: 0 2px 2px 0;
          }

          .svg-icon {
            transform: scale(1.1);
          }
        }
      }
    }

    :deep(.el-menu--inline) {
      background-color: transparent;

      .el-menu-item {
        margin: 2px 10px 2px 20px;
        height: 55px;
        line-height: 55px;
        padding-left: 40px !important;
        border-radius: 6px;

        &.is-active {
          background: linear-gradient(90deg, var(--el-color-primary), var(--el-color-primary-light-3)) !important;
          color: #fff !important;
          position: relative;
          box-shadow: 0 4px 6px rgba(79, 132, 243, 0.3);
          border-radius: 6px;

          &::before {
            content: "";
            position: absolute;
            left: 0;
            top: 0;
            height: 100%;
            width: 4px;
            background-color: #ffffff80;
            border-radius: 0 4px 4px 0;
          }
        }
      }
    }
  }
}

// 添加侧边栏顶部圆角效果
.sidebar-container.has-logo {
  border-top-left-radius: 12px;
}

// 添加侧边栏底部圆角效果
.sidebar-container {
  border-bottom-left-radius: 12px;
}
</style>
