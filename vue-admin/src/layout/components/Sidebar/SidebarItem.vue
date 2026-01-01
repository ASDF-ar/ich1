<template>
  <div v-if="!item.hidden">
    <template v-if="hasOneShowingChild(item.children, item) && (!onlyOneChild.children || onlyOneChild.noShowingChildren) && !item.alwaysShow">
      <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path, onlyOneChild.query)">
        <el-menu-item :index="resolvePath(onlyOneChild.path)" :class="{ 'submenu-title-noDropdown': !isNest }">
          <svg-icon :icon-class="onlyOneChild.meta.icon || (item.meta && item.meta.icon)" :class="{'sub-menu-icon': !isNest}" style="margin-right: 10px;" />
          <template #title>
            <span class="menu-title" :title="hasTitle(onlyOneChild.meta.title)" style="margin-left: 2px;">{{ onlyOneChild.meta.title }}</span>
          </template>
        </el-menu-item>
      </app-link>
    </template>

    <el-sub-menu v-else ref="subMenu" :index="resolvePath(item.path)" teleported>
      <template v-if="item.meta" #title>
        <svg-icon :icon-class="item.meta && item.meta.icon" :class="{'sub-menu-icon': !isNest}" style="margin-right: 10px;" />
        <span class="menu-title" :title="hasTitle(item.meta.title)" style="margin-left: 2px;">{{ item.meta.title }}</span>
      </template>

      <sidebar-item
        v-for="(child, index) in item.children"
        :key="child.path + index"
        :is-nest="true"
        :item="child"
        :base-path="resolvePath(child.path)"
        class="nest-menu"
      />
    </el-sub-menu>
  </div>
</template>

<script setup>
import { isExternal } from '@/utils/validate'
import AppLink from './Link'
import { getNormalPath } from '@/utils/huacai.js'

const props = defineProps({
  // route object
  item: {
    type: Object,
    required: true
  },
  isNest: {
    type: Boolean,
    default: false
  },
  basePath: {
    type: String,
    default: ''
  }
})

const onlyOneChild = ref({})

function hasOneShowingChild(children = [], parent) {
  if (!children) {
    children = []
  }
  const showingChildren = children.filter(item => {
    if (item.hidden) {
      return false
    }
    onlyOneChild.value = item
    return true
  })

  // When there is only one child router, the child router is displayed by default
  if (showingChildren.length === 1) {
    return true
  }

  // Show parent if there are no child router to display
  if (showingChildren.length === 0) {
    onlyOneChild.value = { ...parent, path: '', noShowingChildren: true }
    return true
  }

  return false
}

function resolvePath(routePath, routeQuery) {
  if (isExternal(routePath)) {
    return routePath
  }
  if (isExternal(props.basePath)) {
    return props.basePath
  }
  if (routeQuery) {
    let query = JSON.parse(routeQuery)
    return { path: getNormalPath(props.basePath + '/' + routePath), query: query }
  }
  return getNormalPath(props.basePath + '/' + routePath)
}

function hasTitle(title){
  if (title.length > 5) {
    return title
  } else {
    return ""
  }
}
</script>

<style lang="scss" scoped>
.menu-title {
  transition: all 0.3s ease;
  margin-left: 8px;
  font-weight: 400;
}

.sub-menu-icon {
  transition: all 0.3s ease;
  font-size: 16px;
}

.el-sub-menu,
.el-menu-item {
  .sub-menu-icon {
    transform: scale(1);
  }

  .menu-title {
    transform: translateX(0);
  }

  &:hover {
    .sub-menu-icon {
      transform: scale(1.2);
    }

    .menu-title {
      transform: translateX(0);
    }
  }
}

// 为子菜单添加缩进和特殊样式
.nest-menu {
  :deep(.el-menu-item) {
    padding-left: 48px !important;

    &.is-active {
      &::before {
        left: 20px;
      }
    }
  }
}
</style>
