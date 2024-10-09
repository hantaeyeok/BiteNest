import { useState, ChangeEvent } from 'react'
import Button from '../shared/Button'
import RadioButton from '../shared/RadioButton'
import RecipeCategorySelect from './RecipeCategory'
import { FormValues } from '@models/recipe'
import IngredientsSection from './IngredientSectionForm'
import SubIngredientsSection from './SubIngredientSectionForm'
import SauceSection from './SauceSectionForm'
import StepsSection from './StepsSectionForm'
import TipSection from './section/TipSectionForm'

function Form({ onSubmit }: { onSubmit: (formData: FormData) => void }) {
  const [formValues, setFormValues] = useState<FormValues>({
    title: '',
    description: '',
    titleImage: null,
    time: 0,
    category: '',
    ingredients: [{ name: '', quantity: '' }],
    subIngredients: null,
    sauce: null,
    steps: [{ stepOrder: 0, description: '', image: null }],
    tips: [], // 빈 배열로 팁 초기화
  })

  const [imagePreviews, setImagePreviews] = useState<string[]>([])
  const [titleImagePreview, setTitleImagePreview] = useState<string | null>(
    null,
  ) // 타이틀 이미지 미리보기 상태

  const handleImageUpload = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0] // 파일이 존재하는지 확인
    if (file) {
      const imageUrl = URL.createObjectURL(file) // 업로드한 파일로 미리보기 URL 생성
      setFormValues((prev) => ({
        ...prev,
        titleImage: file, // 이미지 파일 저장
      }))
      setTitleImagePreview(imageUrl) // 미리보기 URL 설정
    }
  }

  // 이미지 삭제 함수
  const handleRemoveImage = () => {
    setFormValues((prev) => ({
      ...prev,
      titleImage: null, // 이미지 파일 삭제
    }))
    setTitleImagePreview(null) // 미리보기 이미지 삭제
  }

  const handleAddIngredient = () => {
    setFormValues((prev) => ({
      ...prev,
      ingredients: [...prev.ingredients, { name: '', quantity: '' }],
    }))
  }

  const handleRemoveIngredient = (index: number) => {
    const newIngredients = formValues.ingredients.filter((_, i) => i !== index)
    setFormValues({ ...formValues, ingredients: newIngredients })
  }

  const handleIngredientChange = (
    index: number,
    field: 'name' | 'quantity',
    value: string,
  ) => {
    const newIngredients = [...formValues.ingredients]
    newIngredients[index][field] = value
    setFormValues((prev) => ({ ...prev, ingredients: newIngredients }))
  }

  const handleAddSubIngredient = () => {
    setFormValues((prev) => ({
      ...prev,
      subIngredients: prev.subIngredients
        ? [...prev.subIngredients, { name: '', quantity: '' }]
        : [{ name: '', quantity: '' }],
    }))
  }

  const handleRemoveSubIngredient = (index: number) => {
    const newSubIngredients = (formValues.subIngredients || []).filter(
      (_, i) => i !== index,
    )
    setFormValues({ ...formValues, subIngredients: newSubIngredients })
  }

  const handleSubIngredientChange = (
    index: number,
    field: 'name' | 'quantity',
    value: string,
  ) => {
    const newSubIngredients = [...(formValues.subIngredients || [])]
    newSubIngredients[index][field] = value
    setFormValues((prev) => ({ ...prev, subIngredients: newSubIngredients }))
  }

  const handleAddSauce = () => {
    setFormValues((prev) => ({
      ...prev,
      sauce: prev.sauce
        ? [...prev.sauce, { name: '', quantity: '' }]
        : [{ name: '', quantity: '' }],
    }))
  }

  const handleRemoveSauce = (index: number) => {
    const newSauce = (formValues.sauce || []).filter((_, i) => i !== index)
    setFormValues({ ...formValues, sauce: newSauce })
  }

  const handleSauceChange = (
    index: number,
    field: 'name' | 'quantity',
    value: string,
  ) => {
    const newSauce = [...(formValues.sauce || [])]
    newSauce[index][field] = value
    setFormValues((prev) => ({ ...prev, sauce: newSauce }))
  }

  const handleAddStep = () => {
    setFormValues((prev) => ({
      ...prev,
      steps: [
        ...prev.steps,
        { stepOrder: prev.steps.length + 1, description: '', image: null },
      ],
    }))
    setImagePreviews((prev) => [...prev, ''])
  }

  const handleRemoveStep = (index: number) => {
    const newSteps = formValues.steps.filter((_, i) => i !== index)
    const newPreviews = imagePreviews.filter((_, i) => i !== index)
    setFormValues((prev) => ({
      ...prev,
      steps: newSteps,
    }))
    setImagePreviews(newPreviews)
  }

  const handleStepChange = (index: number, value: string) => {
    const newSteps = [...formValues.steps]
    newSteps[index].description = value
    setFormValues((prev) => ({ ...prev, steps: newSteps }))
  }

  const handleImageChange = (index: number, value: File | null) => {
    const newSteps = [...formValues.steps]
    newSteps[index].image = value
    setFormValues((prev) => ({ ...prev, steps: newSteps }))

    if (value) {
      const previewUrl = URL.createObjectURL(value)
      const newPreviews = [...imagePreviews]
      newPreviews[index] = previewUrl
      setImagePreviews(newPreviews)
    } else {
      const newPreviews = [...imagePreviews]
      newPreviews[index] = ''
      setImagePreviews(newPreviews)
    }
  }

  const handleAddTip = () => {
    setFormValues((prev) => ({
      ...prev,
      tips: [...(prev.tips || []), ''],
    }))
  }

  const handleTipChange = (index: number, value: string) => {
    setFormValues((prev) => {
      const newTips = [...(prev.tips || [])]
      newTips[index] = value
      return { ...prev, tips: newTips }
    })
  }

  const handleRemoveTip = (index: number) => {
    setFormValues((prev) => {
      const newTips = (prev.tips || []).filter((_, i) => i !== index)
      return { ...prev, tips: newTips }
    })
  }

  const timeOptions = [
    { label: '15분 미만', value: 10 },
    { label: '15분컷', value: 15 },
    { label: '30분컷', value: 30 },
    { label: '45분 이상', value: 45 },
  ]

  const handleTimeChange = (e: ChangeEvent<HTMLInputElement>) => {
    const selectedValue = parseInt(e.target.value)
    setFormValues((prev) => ({
      ...prev,
      time: selectedValue,
    }))
  }

  const handleSubmit = () => {
    const formData = new FormData()

    formData.append('title', formValues.title)
    formData.append('description', formValues.description)
    formData.append('time', String(formValues.time))
    formData.append('category', formValues.category)

    if (formValues.titleImage) {
      formData.append('titleImage', formValues.titleImage) // 이미지 파일 추가
    }

    // ingredients 배열 추가
    formValues.ingredients.forEach((ingredient, index) => {
      formData.append(`ingredients[${index}].ingredientName`, ingredient.name)
      formData.append(
        `ingredients[${index}].ingredientAmount`,
        ingredient.quantity,
      )
      formData.append(`ingredients[${index}].ingredientType`, '주재료')
    })

    // subIngredients가 있을 경우 추가
    if (formValues.subIngredients) {
      formValues.subIngredients.forEach((subIngredient, index) => {
        formData.append(
          `ingredients[${index + formValues.ingredients.length}].ingredientName`,
          subIngredient.name || '',
        )
        formData.append(
          `ingredients[${index + formValues.ingredients.length}].ingredientAmount`,
          subIngredient.quantity || '',
        )
        formData.append(
          `ingredients[${index + formValues.ingredients.length}].ingredientType`,
          '부재료',
        )
      })
    }

    // sauce가 있을 경우 추가
    if (formValues.sauce) {
      formValues.sauce.forEach((sauceItem, index) => {
        const baseIndex =
          index +
          formValues.ingredients.length +
          (formValues.subIngredients?.length || 0)
        formData.append(
          `ingredients[${baseIndex}].ingredientName`,
          sauceItem.name || '',
        )
        formData.append(
          `ingredients[${baseIndex}].ingredientAmount`,
          sauceItem.quantity || '',
        )
        formData.append(`ingredients[${baseIndex}].ingredientType`, '양념')
      })
    }

    // steps 배열에서 파일 처리
    formValues.steps.forEach((step, index) => {
      formData.append(`steps[${index}].description`, step.description)
      if (step.image) {
        formData.append(`steps[${index}].image`, step.image)
      }
    })

    if (formValues.tips && formValues.tips.length > 0) {
      formValues.tips.forEach((tip, index) => {
        formData.append(`tips[${index}]`, tip)
      })
    }

    onSubmit(formData)
  }

  return (
    <div className="sm:container py-6 px-6 sm:px-36 text-brown-300 min-w-[300px] ">
      <div className="flex justify-center items-center justify-between space-x-4 ">
        <IconLeft />
        <div className="min-w-24">
          <p className="font-bold">레시피 작성</p>
          <p className="text-xs">작성중인 레시피</p>
        </div>
        <Button
          label="완료"
          size="medium"
          color="bg-brown-100"
          onClick={handleSubmit}
        />
      </div>

      {/* 이미지 미리보기 */}
      <div className="bg-creamWhite h-80 flex items-center justify-center relative ">
        {titleImagePreview ? (
          <div className="relative h-full ">
            <img
              src={titleImagePreview}
              alt="미리보기"
              className="h-full w-full object-contain cursor-pointer" // object-fit: contain 적용
              onClick={() => document.getElementById('fileInput')?.click()}
            />
            {/* 이미지 삭제 버튼 */}
            <button
              className="absolute top-2 right-2 bg-brown-300 text-white px-2 py-1 rounded z-10"
              onClick={handleRemoveImage}
            >
              삭제
            </button>
          </div>
        ) : (
          <button onClick={() => document.getElementById('fileInput')?.click()}>
            이미지 미리보기
          </button>
        )}
      </div>

      {/* 파일 업로드 input */}
      <input
        id="fileInput"
        type="file"
        className="hidden"
        accept="image/*"
        onChange={handleImageUpload} // 파일 선택 시 handleImageUpload 함수 실행
      />

      <input
        type="text"
        className="mt-1 p-2 border border-brown-100 w-full rounded-md"
        placeholder="레시피 이름을 알려주세요 (20자)"
        value={formValues.title}
        onChange={(e) =>
          setFormValues({ ...formValues, title: e.target.value })
        }
        required
      />

      <textarea
        className="mt-1 p-2 border border-brown-100 w-full rounded-md h-48 resize-none"
        placeholder="레시피의 간단한 설명을 만들어주세요 (150자)"
        value={formValues.description}
        onChange={(e) =>
          setFormValues({ ...formValues, description: e.target.value })
        }
        required
      />

      <div className="my-6"></div>
      <div>
        <label className="block text-lg font-bold text-brown-300">
          예상 소요시간*
        </label>
        <div className="flex space-x-4 mt-2">
          {timeOptions.map((option) => (
            <RadioButton
              key={option.value}
              label={option.label}
              name="time"
              value={option.value}
              onChange={handleTimeChange}
            />
          ))}
        </div>
      </div>

      <IngredientsSection
        ingredients={formValues.ingredients}
        handleAddIngredient={handleAddIngredient}
        handleIngredientChange={handleIngredientChange}
        handleRemoveIngredient={handleRemoveIngredient}
      />

      <SubIngredientsSection
        subIngredients={formValues.subIngredients}
        handleAddSubIngredient={handleAddSubIngredient}
        handleSubIngredientChange={handleSubIngredientChange}
        handleRemoveSubIngredient={handleRemoveSubIngredient}
      />

      <SauceSection
        sauce={formValues.sauce}
        handleAddSauce={handleAddSauce}
        handleSauceChange={handleSauceChange}
        handleRemoveSauce={handleRemoveSauce}
      />

      <StepsSection
        steps={formValues.steps}
        handleAddStep={handleAddStep}
        handleStepChange={handleStepChange}
        handleRemoveStep={handleRemoveStep}
        handleImageChange={handleImageChange}
        imagePreviews={imagePreviews}
      />

      <TipSection
        tips={formValues.tips}
        handleAddTip={handleAddTip}
        handleTipChange={handleTipChange}
        handleRemoveTip={handleRemoveTip}
      />
    </div>
  )
}

function IconLeft() {
  return (
    <svg
      className="feather feather-chevron-left"
      fill="none"
      height="24"
      stroke="currentColor"
      stroke-linecap="round"
      stroke-linejoin="round"
      stroke-width="2"
      viewBox="0 0 24 24"
      width="24"
      xmlns="http://www.w3.org/2000/svg"
    >
      <polyline points="15 18 9 12 15 6" />
    </svg>
  )
}

export default Form
