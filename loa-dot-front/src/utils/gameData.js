export const GRADE_COLORS = {
  '일반': '#ffffff',
  '고급': '#00b050',
  '희귀': '#00b5ff',
  '영웅': '#ce43fc',
  '전설': '#ff9900',
  '유물': '#ea6000',
  '고대': '#CDB89A',
  '에스더': '#4afbe6'
}

export const getGradeColor = (grade) => GRADE_COLORS[grade] || '#94a3b8'

export const QUALITY_COLORS = {
  NONE: '#a6a6a6',
  RED: '#ff3e3e',
  GREEN: '#00b050',
  BLUE: '#00b5ff',
  PURPLE: '#ce43fc',
  ORANGE: '#fe9600'
}

export const getQualityColor = (quality) => {
  // null, undefined, 또는 0 이하일 때
  if (quality === null || quality === undefined || quality < 0) {
    return QUALITY_COLORS.NONE;
  }
  
  if (quality === 100) return QUALITY_COLORS.ORANGE;
  if (quality >= 90)  return QUALITY_COLORS.PURPLE;
  if (quality >= 70)  return QUALITY_COLORS.BLUE;
  if (quality >= 31)  return QUALITY_COLORS.GREEN;
  if (quality >= 1)   return QUALITY_COLORS.RED;

  return QUALITY_COLORS.NONE;
}