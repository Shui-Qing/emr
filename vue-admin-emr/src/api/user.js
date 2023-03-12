import request from '@/utils/request'

const baseUrl = 'http://localhost:9528'

export function login(data) {
  return request({
    url: `${baseUrl}/vue-admin-template/user/login`,
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: `${baseUrl}/vue-admin-template/user/info`,
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}
