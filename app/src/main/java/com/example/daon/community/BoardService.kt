package com.example.daon.community

import com.example.daon.community.token.UploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface BoardService {
    //커뮤니티
    //게시판 전체
    @GET("/board/get-all-allType")
    fun getAllAllPosts(@Query("offset") offset: Int): Call<PostListCallResponseDto>
    //게시판 목록
    @GET("/board/get-all/{boardType}")
    fun getAllPosts(@Path("boardType") boardType: String, @Query("offset") offset: Int): Call<PostListCallResponseDto>
    // 특정 게시물
    @GET("/board/get-one/{boardId}")
    fun getPost(@Path("boardId") boardId: String): Call<PostOneListResponseDto>
    // 게시판 작성
    @POST("/board/write/post/{boardType}")
    fun writePost(@Path("boardType") boardType: String, @Body post: PostWriteRequestDto): Call<PostWriteResponseDto>
    // 게시판 수정
    @PUT("/board/write/put/{boardId}")
    fun updatePost(@Path("boardId") boardId: String, @Body post: PostWriteRequestDto): Call<PostWriteResponseDto>
    // 게시판 삭제
    @DELETE("/board/write/delete/{boardId}")
    fun deletePost(@Path("boardId") boardId: String): Call<PostWriteResponseDto>
    // 좋아요 추가
    @POST("/board/like-up/{boardId}")
    fun likeUp(@Path("boardId") boardId: String): Call<LikeResponse>
    // 좋아요 삭제
    @DELETE("/board/like-down/{boardId}")
    fun likeDown(@Path("boardId") boardId: String): Call<LikeResponse>
    // 특정 글 좋아요 수 가져오기
    @GET("/board/like/{boardId}")
    fun getLikesForPost(@Path("boardId") boardId: String): Call<LikeResponse>
    // 좋아요 전체 가져오기
    @GET("/board/all-like")
    fun getAllLikes(@Query("offset") offset: Int): Call<LikeResponse>
    // 스크랩 추가
    @POST("/board/add-scrape/{boardId}")
    fun addScrape(@Path("boardId") boardId: String): Call<ScrapeResponse>
    // 스크랩 삭제
    @DELETE("/board/sub-scrape/{boardId}")
    fun subScrape(@Path("boardId") boardId: String): Call<ScrapeResponse>
    // 댓글 작성하기
    @POST("/board/comment/post/{boardId}")
    fun postComment(@Path("boardId") boardId: String, @Body comment: CommentRequestDto): Call<CommentResponseDto>
    // 댓글 수정하기
    @PUT("/board/comment/put/{commentId}")
    fun putComment(@Path("commentId") commentId: String, @Body comment: CommentRequestDto): Call<CommentResponseDto>
    // 댓글 삭제하기
    @DELETE("/board/comment/delete/{commentId}")
    fun deleteComment(@Path("commentId") commentId: String, @Query("offset") offset: Int): Call<CommentResponseDto>
    // 댓글 가져오기
    @GET("/board/comment/get/{boardId}")
    fun getComments(@Path("boardId") boardId: String, @Query("offset") offset: Int): Call<List<CommentResponseDto>>
    // 댓글 좋아요 추가
    @POST("/board/comment/like-up/{commentId}")
    fun likeUpComment(@Path("commentId") commentId: String): Call<LikeResponse>
    // 댓글 좋아요 삭제
    @DELETE("/board/comment/like-down/{commentId}")
    fun likeDownComment(@Path("commentId") commentId: String): Call<LikeResponse>
    @Multipart
    @POST("/upload/board")
    fun uploadBoardImage(@Part file: MultipartBody.Part): Call<UploadResponse>
}
