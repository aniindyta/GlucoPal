package com.dicoding.glucopal.data

import com.dicoding.glucopal.data.pref.UserPreference
import com.dicoding.glucopal.data.response.CategoryResponse
import com.dicoding.glucopal.data.response.LoginResponse
import com.dicoding.glucopal.data.response.LoginResult
import com.dicoding.glucopal.data.response.RegisterResponse
import com.dicoding.glucopal.data.retrofit.ApiService
import com.dicoding.glucopal.utils.Gender
import kotlinx.coroutines.flow.Flow

class Repository (
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    suspend fun saveSession(user: LoginResult) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<LoginResult> {
        return userPreference.getSession()
    }

    /*suspend fun logout() {
        userPreference.logout()
    }*/

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(LoginData(email, password))
    }

    suspend fun register(username: String, email: String, password: String, repeatPassword: String, gender: Gender): RegisterResponse {
        return apiService.register(RegistrationData(username, email, password, repeatPassword, gender))
    }

    suspend fun getCategory(): CategoryResponse {
        return apiService.getCategory()
    }

/*suspend fun getStories(): ListStoryResponse{
        return apiService.getStories()
    }


suspend fun getDetailStory(id: String?): DetailStoryResponse {
        return apiService.getDetailStory(id)
    }


suspend fun upload(description: RequestBody, file: MultipartBody.Part): FileUploadResponse {
        return apiService.uploadImage(file, description)
    }


suspend fun getStoriesWithLocation(): ListStoryResponse {
        return apiService.getStoriesWithLocation()
    }*/


    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, userPreference)
            }.also { instance = it }
    }
}
