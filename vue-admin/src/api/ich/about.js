import request from '@/utils/request'

// 查询关于我们列表
export function listAbout(query) {
  return request({
    url: '/ich/about/list',
    method: 'get',
    params: query
  })
}

// 查询关于我们详细
export function getAbout(aboutId) {
  return request({
    url: '/ich/about/' + aboutId,
    method: 'get'
  })
}

// 新增关于我们
export function addAbout(data) {
  return request({
    url: '/ich/about',
    method: 'post',
    data: data
  })
}

// 修改关于我们
export function updateAbout(data) {
  return request({
    url: '/ich/about',
    method: 'put',
    data: data
  })
}

// 删除关于我们
export function delAbout(aboutId) {
  return request({
    url: '/ich/about/' + aboutId,
    method: 'delete'
  })
}

// 查询关于我们表中有没有数据
export function selectIsExist() {
  return request({
    url: '/ich/about/selectIsExist',
    method: 'get'
  })
}

//查询当前的关于我们数据
export function selectCurrentAbout() {
  return request({
    url: '/ich/about/selectCurrentAbout',
    method: 'get'
  })
}

