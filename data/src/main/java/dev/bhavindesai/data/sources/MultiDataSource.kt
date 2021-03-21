package dev.bhavindesai.data.sources

import dev.bhavindesai.data.utils.InternetUtil
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class MultiDataSource<LocalType, RequestType, ResponseType>(
    private val internetUtil: InternetUtil
) : LocalDataSource<LocalType>,
    RemoteDataSource<RequestType, ResponseType> {

    abstract fun mapper(remoteData: ResponseType) : LocalType

    fun fetch(request: RequestType) = flow {
        val localData = getLocalData()

        localData?.let { emit(it) }

        if (internetUtil.isInternetOn()) {
            getRemoteData(request)
                .collect {
                    it?.let { remoteData ->
                        val mappedData = mapper(remoteData)
                        storeLocalData(mappedData)

                        emit(mappedData)
                    }
                }
        } else if (localData == null){
            emit(null)
        }
    }
}