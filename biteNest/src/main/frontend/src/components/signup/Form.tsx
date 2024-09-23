import { ChangeEvent, useCallback, useMemo, useState } from 'react'
import Button from '@components/shared/Button'
import TextField from '@components/shared/TextField'

import { FormValues } from '@models/signup'
import validator from 'validator'
import Spacing from '@/shared/Spacing'

function validate(formValues: FormValues) {
  let errors: Partial<FormValues> = {}

  if (validator.isEmail(formValues.email) === false) {
    errors.email = '이메일형식을 확인해주세요'
  }

  if (formValues.password.length < 8) {
    errors.password = '비밀번호를 8자 이상 입력해주세요'
  }

  if (formValues.rePassword.length < 8) {
    errors.rePassword = '비밀번호를 8자 이상 입력해주세요'
  } else if (
    validator.equals(formValues.rePassword, formValues.password) === false
  ) {
    errors.rePassword = '비밀번호를 확인해주세요'
  }

  if (formValues.name.length < 2) {
    errors.name = '이름은 2글자 이상 입력해주세요'
  }
  if (formValues.nickName.length < 2) {
    errors.nickName = '닉네임은 2글자 이상 입력해주세요'
  }
  return errors
}

function Form({ onSubmit }: { onSubmit: (formValues: FormValues) => void }) {
  const [formValues, setFormValues] = useState<FormValues>({
    email: '',
    password: '',
    rePassword: '',
    name: '',
    nickName: '',
  })

  const [dirty, setDirty] = useState<Partial<FormValues>>({})

  const errors = useMemo(() => validate(formValues), [formValues])

  const 제출가능한상태인가 = Object.keys(errors).length === 0

  const handleFormValues = useCallback((e: ChangeEvent<HTMLInputElement>) => {
    setFormValues((prevFormValues) => ({
      ...prevFormValues,
      [e.target.name]: e.target.value,
    }))
  }, [])

  const handleBlur = useCallback((e: ChangeEvent<HTMLInputElement>) => {
    setDirty((prevDirty) => ({
      ...prevDirty,
      [e.target.name]: 'true',
    }))
  }, [])

  return (
    <div className="sm:container sm:mx-auto py-6 px-6 sm:px-36">
      <p className="text-2xl my-6">회원가입</p>
      <TextField
        label="이메일"
        name="email"
        placeholder="prumpy11@naver.com"
        value={formValues.email}
        onChange={handleFormValues}
        hasError={Boolean(dirty.email) && Boolean(errors.email)}
        helpMessage={Boolean(dirty.email) ? errors.email : ''}
        onBlur={handleBlur}
      />
      <Spacing size={6} />
      <TextField
        label="패스워드"
        name="password"
        type="password"
        value={formValues.password}
        onChange={handleFormValues}
        hasError={Boolean(dirty.password) && Boolean(errors.password)}
        helpMessage={Boolean(dirty.password) ? errors.password : ''}
        onBlur={handleBlur}
      />
      <Spacing size={6} />
      <TextField
        label="패스워드확인"
        name="rePassword"
        type="password"
        value={formValues.rePassword}
        onChange={handleFormValues}
        hasError={Boolean(dirty.rePassword) && Boolean(errors.rePassword)}
        helpMessage={Boolean(dirty.rePassword) ? errors.rePassword : ''}
        onBlur={handleBlur}
      />
      <Spacing size={6} />
      <TextField
        label="이름"
        name="name"
        placeholder="홍길동"
        value={formValues.name}
        onChange={handleFormValues}
        hasError={Boolean(dirty.name) && Boolean(errors.name)}
        helpMessage={Boolean(dirty.name) ? errors.name : ''}
        onBlur={handleBlur}
      />
      <Spacing size={6} />
      <TextField
        label="닉네임"
        name="nickName"
        placeholder="캬라멜"
        value={formValues.nickName}
        onChange={handleFormValues}
        hasError={Boolean(dirty.nickName) && Boolean(errors.nickName)}
        helpMessage={Boolean(dirty.nickName) ? errors.nickName : ''}
        onBlur={handleBlur}
      />
      <Spacing size={6} />
      <Button
        label="회원가입"
        disabled={제출가능한상태인가 === false}
        color="bg-brown-100"
        size="full"
        onClick={() => {
          onSubmit(formValues)
        }}
      ></Button>
    </div>
  )
}

export default Form
