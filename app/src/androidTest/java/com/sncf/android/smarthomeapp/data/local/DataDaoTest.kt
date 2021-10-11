package com.sncf.android.smarthomeapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.sncf.android.smarthomeapp.common.livedata.getOrAwaitValue
import com.sncf.android.smarthomeapp.data.model.Address
import com.sncf.android.smarthomeapp.data.model.Device
import com.sncf.android.smarthomeapp.data.model.User
import com.sncf.android.smarthomeapp.utils.Constants.HEATER_LABEL
import com.sncf.android.smarthomeapp.utils.Constants.LIGHT_LABEL
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DataDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: LocalDatabase
    private lateinit var dao: DataDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), LocalDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getDataDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertDeviceTest() {
        runBlocking {
            // Arrange
            val device = Device(1, "Lampe", 12, "ON", productType = LIGHT_LABEL)

            // Act
            dao.insertDevice(device)

            // Assert
            val devices = dao.getDevices().getOrAwaitValue()
            assertTrue(devices.size == 1)
            assertTrue(devices[0].deviceName == "Lampe")
        }
    }

    @Test
    fun deleteDeviceTest() {
        runBlocking {
            // Arrange
            val device =
                Device(1, "Heater", temperature = 12, mode = "ON", productType = HEATER_LABEL)
            dao.insertDevice(device)

            // Act
            dao.deleteDevice(device)

            // Assert
            val devices = dao.getDevices().getOrAwaitValue()
            assertTrue(devices.isEmpty())
        }
    }

    @Test
    fun updateDeviceTest() {
        runBlocking {
            // Arrange
            val device =
                Device(1, "Heater", temperature = 12, mode = "ON", productType = HEATER_LABEL)
            dao.insertDevice(device)
            device.temperature = 20

            // Act
            dao.updateDevice(device)

            // Assert
            val devices = dao.getDevices().getOrAwaitValue()
            assertTrue(devices.size == 1)
            assertTrue(devices[0].temperature == 20)
        }
    }

    @Test
    fun insertUserTest() {
        runBlocking {
            // Arrange
            val user = User(
                1, "Jean", "Dupont", Address(
                    "Paris", 75000, "street", "20", "France"
                ), 813766371000
            )

            // Act
            dao.insertUser(user)

            // Assert
            val insertedUser = dao.getUser().getOrAwaitValue()
            assertTrue(insertedUser != null)
            assertTrue(insertedUser?.firstName == "Jean")
        }
    }

    @Test
    fun updateUserTest() {
        runBlocking {
            // Arrange
            val user = User(
                1, "Jean", "Dupont", Address(
                    "Paris", 75000, "street", "20", "France"
                ), 813766371000
            )

            dao.insertUser(user)
            user.lastName = "Patric"

            // Act
            dao.updateUser(user)

            // Assert
            val updatedUser = dao.getUser().getOrAwaitValue()
            assertTrue(updatedUser != null)
            assertTrue(updatedUser?.lastName == "Patric")
        }
    }
}