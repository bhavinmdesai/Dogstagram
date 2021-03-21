package dev.bhavindesai.data.sources

import kotlinx.coroutines.flow.Flow

interface RemoteDataSource<RequestType, ResponseType> {
    fun getRemoteData(requestData: RequestType) : Flow<ResponseType?>
}