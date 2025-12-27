import request from '@/utils/request'

// 查询非遗项目列表
export function listHeritage(query) {
  return request({
    url: '/ich/heritage/list',
    method: 'get',
    params: query
  })
}

// 查询非遗项目详细
export function getHeritage(heritageId) {
  return request({
    url: '/ich/heritage/' + heritageId,
    method: 'get'
  })
}

// 新增非遗项目
export function addHeritage(data) {
  return request({
    url: '/ich/heritage',
    method: 'post',
    data: data
  })
}

// 修改非遗项目
export function updateHeritage(data) {
  return request({
    url: '/ich/heritage',
    method: 'put',
    data: data
  })
}

// 删除非遗项目
export function delHeritage(heritageId) {
  return request({
    url: '/ich/heritage/' + heritageId,
    method: 'delete'
  })
}

//提取唯一分类字段返回给前端
// 提取唯一分类字段并返回给前端
export function selectOnlyCategories() {
  return request({
    url: '/ich/heritage/selectOnlyCategories',
    method: 'get'
  })
}