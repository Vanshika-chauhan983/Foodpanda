package com.vanshika.foodpanda.DataClasses

data class FoodData(
    val count: Int,
    val results: List<Result>
)

data class Result(
    val approved_at: Int,
    val aspect_ratio: String,
    val beauty_url: Any,
    val brand: Any,
    val brand_id: Any,
    val buzz_id: Int,
    val canonical_id: String,
    val compilations: List<Compilation>,
    val cook_time_minutes: Int,
    val country: String,
    val created_at: Int,
    val credits: List<Credit>,
    val description: String,
    val draft_status: String,
    val facebook_posts: List<Any?>,
    val id: Int,
    val inspired_by_url: String,
    val instructions: List<Instruction>,
    val is_app_only: Boolean,
    val is_one_top: Boolean,
    val is_shoppable: Boolean,
    val is_subscriber_content: Boolean,
    val keywords: String,
    val language: String,
    val name: String,
    val num_servings: Int,
    val nutrition: Nutrition,
    val nutrition_visibility: String,
    val original_video_url: String,
    val prep_time_minutes: Int,
    val price: Price,
    val promotion: String,
    val renditions: List<Rendition>,
    val sections: List<Section>,
    val seo_path: String,
    val seo_title: String,
    val servings_noun_plural: String,
    val servings_noun_singular: String,
    val show: ShowX,
    val show_id: Int,
    val slug: String,
    val tags: List<Tag>,
    val thumbnail_alt_text: String,
    val thumbnail_url: String,
    val tips_and_ratings_enabled: Boolean,
    val tips_summary: TipsSummary,
    val topics: List<Topic>,
    val total_time_minutes: Int,
    val total_time_tier: TotalTimeTier,
    val updated_at: Int,
    val user_ratings: UserRatings,
    val video_ad_content: String,
    val video_id: Int,
    val video_url: String,
    val yields: String
)

data class Compilation(
    val approved_at: Int,
    val aspect_ratio: String,
    val beauty_url: String,
    val buzz_id: Int,
    val canonical_id: String,
    val country: String,
    val created_at: Int,
    val description: String,
    val draft_status: String,
    val facebook_posts: List<Any?>,
    val id: Int,
    val is_shoppable: Boolean,
    val keywords: String,
    val language: String,
    val name: String,
    val promotion: String,
    val show: List<ShowX>,
    val slug: String,
    val thumbnail_alt_text: String,
    val thumbnail_url: String,
    val video_id: Int,
    val video_url: String
)

data class Credit(
    val is_verified: Boolean,
    val name: String,
    val picture_url: String,
    val type: String,
    val user_id: Any
)

data class Instruction(
    val appliance: String,
    val display_text: String,
    val end_time: Int,
    val hacks: List<Hack>,
    val id: Int,
    val position: Int,
    val start_time: Int,
    val temperature: Int
)

data class Nutrition(
    val calories: Int,
    val carbohydrates: Int,
    val fat: Int,
    val fiber: Int,
    val protein: Int,
    val sugar: Int,
    val updated_at: String
)

data class Price(
    val consumption_portion: Int,
    val consumption_total: Int,
    val portion: Int,
    val total: Int,
    val updated_at: String
)

data class Rendition(
    val aspect: String,
    val bit_rate: Int,
    val container: String,
    val content_type: String,
    val duration: Int,
    val file_size: Int,
    val height: Int,
    val maximum_bit_rate: Int,
    val minimum_bit_rate: Int,
    val name: String,
    val poster_url: String,
    val url: String,
    val width: Int
)

data class Section(
    val components: List<Component>,
    val name: String,
    val position: Int
)

data class ShowX(
    val id: Int,
    val name: String
)

data class Tag(
    val display_name: String,
    val id: Int,
    val name: String,
    val parent_tag_name: String,
    val root_tag_type: String,
    val type: String
)

data class TipsSummary(
    val by_line: String,
    val content: String,
    val header: String
)

data class Topic(
    val name: String,
    val slug: String
)

data class TotalTimeTier(
    val display_tier: String,
    val tier: String
)

data class UserRatings(
    val count_negative: Int,
    val count_positive: Int,
    val score: Double
)

data class Hack(
    val end_index: Int,
    val id: Int,
    val match: String,
    val start_index: Int
)

data class Component(
    val extra_comment: String,
    val hacks: List<Hack>,
    val id: Int,
    val ingredient: Ingredient,
    val measurements: List<Measurement>,
    val position: Int,
    val raw_text: String
)

data class Ingredient(
    val created_at: Int,
    val display_plural: String,
    val display_singular: String,
    val id: Int,
    val name: String,
    val updated_at: Int
)

data class Measurement(
    val id: Int,
    val quantity: String,
    val unit: Unit
)

data class Unit(
    val abbreviation: String,
    val display_plural: String,
    val display_singular: String,
    val name: String,
    val system: String
)