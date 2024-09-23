import { rest } from 'msw'

export const handlers = [
  //로그인 POST 요청에 대한 핸들러
  // rest.post('/api/login', (req, res, ctx) => {
  //   const { email, password } = req.body
  //   // 간단한 로그인 로직 (모킹)
  //   if (email === 'admin@naver.com' && password === 'admin123') {
  //     return res(
  //       ctx.status(200),
  //       ctx.json({
  //         message: '로그인 성공',
  //         token: 'mocked-jwt-token',
  //       }),
  //     )
  //   }
  //   return res(
  //     ctx.status(403),
  //     ctx.json({
  //       message: '로그인 실패: 잘못된 자격 증명',
  //     }),
  //   )
  // }),
  // // 다른 API 요청에 대한 예시 핸들러
  // rest.get('/user', (req, res, ctx) => {
  //   return res(
  //     ctx.status(200),
  //     ctx.json({
  //       username: 'admin',
  //       email: 'admin@example.com',
  //     }),
  //   )
  // }),
]
