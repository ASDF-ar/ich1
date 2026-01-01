import request from '@/utils/request'

// 查询活动预告列表
export function listEvent(query) {
  return request({
    url: '/ich/event/list',
    method: 'get',
    params: query
  })
}

// 查询活动预告详细
export function getEvent(eventId) {
  return request({
    url: '/ich/event/' + eventId,
    method: 'get'
  })
}

// 新增活动预告
export function addEvent(data) {
  return request({
    url: '/ich/event',
    method: 'post',
    data: data
  })
}

// 修改活动预告
export function updateEvent(data) {
  return request({
    url: '/ich/event',
    method: 'put',
    data: data
  })
}

// 删除活动预告
export function delEvent(eventId) {
  return request({
    url: '/ich/event/' + eventId,
    method: 'delete'
  })
}
