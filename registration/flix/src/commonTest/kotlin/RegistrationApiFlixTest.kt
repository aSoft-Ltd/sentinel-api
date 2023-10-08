import kommander.expect
import kommander.expectFailure
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import sentinel.RegistrationApi
import sentinel.exceptions.UserAlreadyCompletedRegistrationException
import sentinel.exceptions.UserDidNotBeginRegistrationException
import sentinel.params.SignUpParams
import kotlin.test.Test

class RegistrationApiFlixTest {

    private val api: RegistrationApi get() = TODO()


    @Test
    fun should_be_able_to_begin_the_registration_process() = runTest {
        val res = api.signUp(SignUpParams("Anderson", "andy@lamax.com")).await()
        expect(res.email).toBe("andy@lamax.com")
    }

    @Test
    fun should_fail_to_sign_up_an_already_verified_account() = runTest {
        api.signUp(SignUpParams("Anderson", "andy@lamax.com")).await()
        val exp = expectFailure {
            api.signUp(SignUpParams("Anderson", "andy@lamax.com")).await()
        }
        expect(exp.message).toBe(UserAlreadyCompletedRegistrationException("andy@lamax.com").message)
    }

    @Test
    fun should_be_able_to_send_email_verification_for_a_user_who_has_began_the_registration_process() = runTest {
        val res = api.signUp(SignUpParams("Anderson", "anderson@lamax.com")).await()
        api.sendVerificationLink(res.email).await()
    }

    @Test
    fun should_be_able_to_complete_registration() = runTest {
        val res = api.signUp(SignUpParams("Anderson", "anderson@lamax.com")).await()
        api.sendVerificationLink(res.email).await()
//        api.verify(VerificationParams(email = res.email,token = token)).await()
    }

    @Test
    fun should_fail_to_send_an_email_verification_for_a_user_who_has_not_began_the_registration_process() = runTest {
        val email = "juma@yahoo.com"
        val exp = expectFailure { api.sendVerificationLink(email).await() }
        expect(exp.message).toBe(UserDidNotBeginRegistrationException("juma@yahoo.com").message)
    }
}