import axios from 'axios'

const instance = axios.create({
  baseURL: 'http://localhost:80/api', // 적절한 포트를 사용
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 5000, // 요청 타임아웃 (옵션)
})

export default instance
