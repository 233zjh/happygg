import request from '@/utils/request'

// 查询图片或视频列表
export function listItem(query) {
  return request({
    url: '/gaogao/item/list',
    method: 'get',
    params: query
  })
}

// 查询图片或视频详细
export function getItem(id) {
  return request({
    url: '/gaogao/item/' + id,
    method: 'get'
  })
}

// 新增图片或视频
export function addItem(data) {
  return request({
    url: '/gaogao/item',
    method: 'post',
    data: data
  })
}

// 修改图片或视频
export function updateItem(data) {
  return request({
    url: '/gaogao/item',
    method: 'put',
    data: data
  })
}

// 删除图片或视频
export function delItem(id) {
  return request({
    url: '/gaogao/item/' + id,
    method: 'delete'
  })
}

export function hideItem(id, status) {
  return request({
    url: '/gaogao/item/hidden',
    method: 'post',
    data: {id: id, isHidden: status}
  })
}
