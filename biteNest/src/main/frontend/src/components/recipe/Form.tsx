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
    subIngredients: [],
    sauce: [],
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

  const handleAddItem = (field: keyof FormValues) => {
    setFormValues((prev) => ({
      ...prev,
      [field]: [...(prev[field] as any[]), { name: '', quantity: '' }],
    }))
  }

  const handleRemoveItem = (field: keyof FormValues, index: number) => {
    setFormValues((prev) => ({
      ...prev,
      [field]: (prev[field] as any[]).filter((_, i: number) => i !== index),
    }))
  }

  const handleItemChange = (
    field: keyof FormValues,
    index: number,
    subField: string,
    value: string,
  ) => {
    setFormValues((prev) => {
      const updatedItems = [...(prev[field] as any[])]
      updatedItems[index][subField] = value
      return { ...prev, [field]: updatedItems }
    })
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
    setFormValues((prev) => ({
      ...prev,
      steps: prev.steps.filter((_, i) => i !== index),
    }))
    setImagePreviews((prev) => prev.filter((_, i) => i !== index))
  }

  const handleStepChange = (index: number, value: string) => {
    setFormValues((prev) => {
      const newSteps = [...prev.steps]
      newSteps[index].description = value
      return { ...prev, steps: newSteps }
    })
  }

  const handleImageChange = (index: number, value: File | null) => {
    setFormValues((prev) => {
      const newSteps = [...prev.steps]
      newSteps[index].image = value
      return { ...prev, steps: newSteps }
    })

    if (value) {
      const previewUrl = URL.createObjectURL(value)
      setImagePreviews((prev) => {
        const newPreviews = [...prev]
        newPreviews[index] = previewUrl
        return newPreviews
      })
    } else {
      setImagePreviews((prev) => {
        const newPreviews = [...prev]
        newPreviews[index] = ''
        return newPreviews
      })
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

    formData.append('formData[title]', formValues.title)
    formData.append('formData[description]', formValues.description)
    formData.append('formData[time]', String(formValues.time))
    formData.append('formData[category]', formValues.category)

    if (formValues.titleImage) {
      formData.append('imageFiles', formValues.titleImage)
    }

    formValues.ingredients.forEach((ingredient, index) => {
      formData.append(`ingredients[${index}][name]`, ingredient.name || '')
      formData.append(
        `ingredients[${index}][quantity]`,
        ingredient.quantity || '',
      )
    })

    if (formValues.subIngredients) {
      formValues.subIngredients.forEach((subIngredient, index) => {
        formData.append(
          `ingredients[${index + formValues.ingredients.length}][name]`,
          subIngredient.name || '',
        )
        formData.append(
          `ingredients[${index + formValues.ingredients.length}][quantity]`,
          subIngredient.quantity || '',
        )
      })
    }

    if (formValues.sauce) {
      formValues.sauce.forEach((sauceItem, index) => {
        const baseIndex =
          index +
          formValues.ingredients.length +
          (formValues.subIngredients?.length || 0)
        formData.append(`ingredients[${baseIndex}][name]`, sauceItem.name || '')
        formData.append(
          `ingredients[${baseIndex}][quantity]`,
          sauceItem.quantity || '',
        )
      })
    }

    formValues.steps.forEach((step, index) => {
      formData.append(`steps[${index}][description]`, step.description)
      if (step.image) {
        formData.append('imageFiles', step.image)
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
              className="h-full w-full object-contain cursor-pointer"
              onClick={() => document.getElementById('fileInput')?.click()}
            />
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

      <input
        id="fileInput"
        type="file"
        className="hidden"
        accept="image/*"
        onChange={handleImageUpload}
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
        handleAddIngredient={() => handleAddItem('ingredients')}
        handleIngredientChange={(index, field, value) =>
          handleItemChange('ingredients', index, field, value)
        }
        handleRemoveIngredient={(index) =>
          handleRemoveItem('ingredients', index)
        }
      />

      <SubIngredientsSection
        subIngredients={formValues.subIngredients}
        handleAddSubIngredient={() => handleAddItem('subIngredients')}
        handleSubIngredientChange={(index, field, value) =>
          handleItemChange('subIngredients', index, field, value)
        }
        handleRemoveSubIngredient={(index) =>
          handleRemoveItem('subIngredients', index)
        }
      />

      <SauceSection
        sauce={formValues.sauce}
        handleAddSauce={() => handleAddItem('sauce')}
        handleSauceChange={(index, field, value) =>
          handleItemChange('sauce', index, field, value)
        }
        handleRemoveSauce={(index) => handleRemoveItem('sauce', index)}
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
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      viewBox="0 0 24 24"
      width="24"
      xmlns="http://www.w3.org/2000/svg"
    >
      <polyline points="15 18 9 12 15 6" />
    </svg>
  )
}

export default Form
