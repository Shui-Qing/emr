import request from '@/utils/request'

const baseUrl = 'http://localhost:8879/api/emr'

export default {
  // 保存
  save(emr) {
    return request({
      url: `${baseUrl}/save`,
      method: 'post',
      data: emr
    })
  },
  getTree() {
    return request({
      url: `${baseUrl}/getTree`,
      method: 'get'
    })
  },
  getById(id){
    return request({
      url: `${baseUrl}/getById/${id}`,
      method: 'get'
    })
  }
}
