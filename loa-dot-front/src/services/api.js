import axios from 'axios';

// API 기본 URL 설정
const API_BASE_URL = 'http://localhost:8081/api';

// Axios 인스턴스 생성
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000, // 10초 타임아웃
  headers: {
    'Content-Type': 'application/json',
  }
});

// 요청 인터셉터
apiClient.interceptors.request.use(
  (config) => {
    // 요청 전 처리
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터
apiClient.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    // 에러 처리
    if (error.response?.status === 401) {
      // 인증 에러 처리
    }
    return Promise.reject(error);
  }
);

/**
 * 캐릭터 API
 */
export const characterAPI = {
  /**
   * 닉네임으로 캐릭터 검색
   * @param {string} characterName - 검색할 캐릭터 닉네임
   * @returns {Promise<Object>} 캐릭터 데이터
   */
  async getCharacterByName(characterName) {
    try {
      const response = await apiClient.get(`/characters/${characterName}`);
      return {
        success: true,
        data: response.data
      };
    } catch (error) {
      return {
        success: false,
        error: error.response?.data?.message || '캐릭터를 찾을 수 없거나 서버 오류입니다.',
        status: error.response?.status
      };
    }
  },
};

/**
 * 길드 API
 */
export const guildAPI = {
  /**
   * 길드 정보 조회
   * @param {string} guildName - 길드명
   * @returns {Promise<Object>} 길드 데이터
   */
  async getGuildInfo(guildName) {
    try {
      const response = await apiClient.get(`/guilds/${guildName}`);
      return {
        success: true,
        data: response.data
      };
    } catch (error) {
      return {
        success: false,
        error: error.response?.data?.message || '길드를 찾을 수 없습니다.',
        status: error.response?.status
      };
    }
  }
};

// 기본 export (필요시)
export default apiClient;