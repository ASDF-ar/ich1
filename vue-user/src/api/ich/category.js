import request from '@/utils/request'

// 查询项目申报类别列表
export function listCategory(query) {
  return request({
    url: '/ich/category/list',
    method: 'get',
    params: query
  })
}

// 查询项目申报类别详细
export function getCategory(pcId) {
  return request({
    url: '/ich/category/' + pcId,
    method: 'get'
  })
}

// 新增项目申报类别
export function addCategory(data) {
  return request({
    url: '/ich/category',
    method: 'post',
    data: data
  })
}

// 修改项目申报类别
export function updateCategory(data) {
  return request({
    url: '/ich/category',
    method: 'put',
    data: data
  })
}

// 删除项目申报类别
export function delCategory(pcId) {
  return request({
    url: '/ich/category/' + pcId,
    method: 'delete'
  })
}
// 查询所有项目申报类别
export function selectAllCategoryList() {
  return request({
    url: '/ich/category/selectAllCategoryList',
    method: 'get'
  })
}