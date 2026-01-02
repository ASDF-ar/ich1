import request from '@/utils/request'

// 查询传承人列表
export function listMaster(query) {
  return request({
    url: '/ich/master/list',
    method: 'get',
    params: query
  })
}

// 查询传承人详细
export function getMaster(masterId) {
  return request({
    url: '/ich/master/' + masterId,
    method: 'get'
  })
}

// 新增传承人
export function addMaster(data) {
  return request({
    url: '/ich/master',
    method: 'post',
    data: data
  })
}

// 修改传承人
export function updateMaster(data) {
  return request({
    url: '/ich/master',
    method: 'put',
    data: data
  })
}

// 删除传承人
export function delMaster(masterId) {
  return request({
    url: '/ich/master/' + masterId,
    method: 'delete'
  })
}
