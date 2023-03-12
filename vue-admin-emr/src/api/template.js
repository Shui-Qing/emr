import request from '@/utils/request'

const baseUrl = 'http://localhost:8879/api/template'

export default {
  // 保存
  save(template) {
    return request({
      url: `${baseUrl}/save`,
      method: 'post',
      data: template
    })
  },
  getDataList(pageIndex, pageSize, key) {
    return request({
      url: `${baseUrl}/list`,
      method: 'get',
      params: {
        'page': pageIndex,
        'limit': pageSize,
        'key': key
      }
    })
  },
  getById(id) {
    return request({
      url: `${baseUrl}/getById/${id}`,
      method: 'get'
    })
  },
  delete(ids) {
    return request({
      url: `${baseUrl}/delete`,
      method: 'post',
      data: ids
    })
  },
  findAll() {
    return request({
      url: `${baseUrl}/findAll`,
      method: 'get'
    })
  }
}
