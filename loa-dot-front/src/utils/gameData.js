export const GRADE_COLORS = {
  '일반': 'var(--loa-grade-common)',
  '고급': 'var(--loa-grade-uncommon)',
  '희귀': 'var(--loa-grade-rare)',
  '영웅': 'var(--loa-grade-epic)',
  '전설': 'var(--loa-grade-legendary)',
  '유물': 'var(--loa-grade-relic)',
  '고대': 'var(--loa-grade-ancient)',
  '에스더': 'var(--loa-grade-esther)'
}

export const getGradeColor = (grade) => GRADE_COLORS[grade] || 'var(--loa-muted)'

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
