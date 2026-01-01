import request from '@/utils/request'

// 查询项目申报列表
export function listProject(query) {
  return request({
    url: '/ich/project/list',
    method: 'get',
    params: query
  })
}

// 查询项目申报详细
export function getProject(projectId) {
  return request({
    url: '/ich/project/' + projectId,
    method: 'get'
  })
}

// 新增项目申报
export function addProject(data) {
  return request({
    url: '/ich/project',
    method: 'post',
    data: data
  })
}

// 修改项目申报
export function updateProject(data) {
  return request({
    url: '/ich/project',
    method: 'put',
    data: data
  })
}

// 删除项目申报
export function delProject(projectId) {
  return request({
    url: '/ich/project/' + projectId,
    method: 'delete'
  })
}
