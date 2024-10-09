import { ChangeEvent, useCallback, useState, useMemo } from 'react'
import { Link } from 'react-router-dom'
import validator from 'validator'
import { FormValues } from '@models/signin'

import Button from '../shared/Button'
import TextField from '../shared/TextField'

function Form({ onSubmit }: { onSubmit: (formValue: FormValues) => void }) {
  const [formValues, setFormValues] = useState({
    email: '',
    password: '',
  })

  const handleFormValues = useCallback((e: ChangeEvent<HTMLInputElement>) => {
    setFormValues((prevFormValues) => ({
      ...prevFormValues,
      [e.target.name]: e.target.value,
    }))
  }, [])

  const errors = useMemo(() => validate(formValues), [formValues])

  const 제출가능한가 = Object.keys(errors).length === 0

  return (
    <div className="sm:container sm:mx-auto py-6 px-6 sm:px-36">
      <TextField
        label="이메일"
        name="email"
        placeholder="prumpy11@naver.com"
        onChange={handleFormValues}
        value={formValues.email}
      />
      <div className="my-6"></div>
      <TextField
        label="패스워드"
        name="password"
        type="password"
        onChange={handleFormValues}
        value={formValues.password}
      />
      <div className="my-6"></div>
      <Button
        label="로그인"
        size="full"
        disabled={제출가능한가 === false}
        onClick={() => onSubmit(formValues)}
      />
      <Link to="/signup" className="flex justify-center">
        <p className="text-xs text-brown-100 hover:text-salmon-100">
          {' '}
          아직 계정이 없으신가요?{' '}
        </p>
      </Link>
    </div>
  )
}

function validate(formValues: FormValues) {
  let errors: Partial<FormValues> = {}

  if (validator.isEmail(formValues.email) === false) {
    errors.email = '이메일형식을 확인해주세요'
  }

  if (formValues.password.length < 8) {
    errors.password = '비밀번호를 8자 이상 입력해주세요'
  }

  return errors
}

export default Form
