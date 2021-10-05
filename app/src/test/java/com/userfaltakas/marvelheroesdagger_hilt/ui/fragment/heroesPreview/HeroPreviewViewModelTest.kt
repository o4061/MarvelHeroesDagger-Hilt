package com.userfaltakas.marvelheroesdagger_hilt.ui.fragment.heroesPreview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import com.userfaltakas.marvelheroesdagger_hilt.MainCoroutineRule
import com.userfaltakas.marvelheroesdagger_hilt.data.api.Result
import com.userfaltakas.marvelheroesdagger_hilt.data.api.Thumbnail
import com.userfaltakas.marvelheroesdagger_hilt.repository.FakeHeroesRepositoryTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HeroPreviewViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HeroPreviewViewModel

    @Before
    fun setup() {
        viewModel = HeroPreviewViewModel(FakeHeroesRepositoryTest())
    }

    @Test
    fun `check for hero inside squad, returns true`() = runBlockingTest {
        viewModel.addHeroToSquad(Result(1, "description", "name", Thumbnail("jpeg", "url")))
        viewModel.addHeroToSquad(Result(2, "description", "name", Thumbnail("jpeg", "url")))
        viewModel.addHeroToSquad(Result(3, "description", "name", Thumbnail("jpeg", "url")))
        viewModel.isHeroExist(2)
        val response = viewModel.heroExist.getOrAwaitValue()
        assertThat(response).isEqualTo(true)
    }

    @Test
    fun `check for hero inside squad, returns false`() = runBlockingTest {
        viewModel.addHeroToSquad(Result(1, "description", "name", Thumbnail("jpeg", "url")))
        viewModel.isHeroExist(5)
        val response = viewModel.heroExist.getOrAwaitValue()
        assertThat(response).isEqualTo(false)
    }

    @Test
    fun `remove hero from squad and check if hero is inside squad, returns false`() =
        runBlockingTest {
            viewModel.addHeroToSquad(Result(1, "description", "name", Thumbnail("jpeg", "url")))
            viewModel.removeHeroFromSquad(
                Result(
                    1,
                    "description",
                    "name",
                    Thumbnail("jpeg", "url")
                )
            )
            viewModel.isHeroExist(1)
            val response = viewModel.heroExist.getOrAwaitValue()
            assertThat(response).isEqualTo(false)
        }

}