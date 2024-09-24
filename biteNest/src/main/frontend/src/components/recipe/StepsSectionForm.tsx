import { useState } from 'react'
import Button from '../shared/Button'

interface Step {
  description: string
  image: File | null
}

interface StepsSectionProps {
  steps: Step[]
  handleAddStep: () => void
  handleStepChange: (index: number, value: string) => void // description만 업데이트
  handleImageChange: (index: number, value: File | null) => void
  handleRemoveStep: (index: number) => void
  imagePreviews: string[] // 미리보기 URL 배열
}

function StepsSection({
  steps,
  handleAddStep,
  handleStepChange,
  handleImageChange,
  handleRemoveStep,
  imagePreviews, // 미리보기 URL 배열을 props로 받아옴
}: StepsSectionProps) {
  return (
    <div>
      <label className="block text-lg font-bold text-brown-300">
        만드는 방법*
      </label>
      <div className="flex flex-col space-y-2">
        {steps.map((step, index) => (
          <div key={index} className="flex flex-col space-x-2">
            <div className="flex">
              <input
                type="text"
                className="mt-1 p-2 border border-brown-100 w-full rounded-md focus:outline-none focus:border-brown-300 focus:ring-brown-300"
                placeholder="단계 설명"
                value={step.description}
                onChange={(e) => handleStepChange(index, e.target.value)}
              />
              <Button
                label="X"
                color="bg-salmon-200 text-white"
                size="small"
                onClick={() => handleRemoveStep(index)}
                disabled={steps.length === 1}
              />
            </div>
            <input
              type="file"
              accept="image/*"
              className="mt-1 border border-brown-100 rounded-md focus:outline-none focus:border-brown-300 focus:ring-brown-300"
              onChange={(e) => {
                const file = e.target.files?.[0] || null
                handleImageChange(index, file)
              }}
            />
            {imagePreviews[index] && (
              <img
                src={imagePreviews[index]}
                alt={`Step ${index + 1}`}
                className="mt-2 w-32 h-32 object-cover border border-brown-100 rounded-md"
              />
            )}
          </div>
        ))}
      </div>
      <Button
        label="추가"
        color="bg-salmon-200 text-salmon-100"
        size="full"
        onClick={handleAddStep}
      />
    </div>
  )
}

export default StepsSection
