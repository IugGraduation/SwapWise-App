package com.example.ui.util

import android.content.Context
import com.example.ui.R
import com.example.ui.base.StringsResource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringsResourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : StringsResource {
    override val emptyEmailMessage: String = getString(R.string.email_can_t_be_empty)

    override val emptyFullNameMessage: String = getString(R.string.full_name_can_t_be_empty)

    override val noConnectionMessage: String = getString(R.string.no_internet_connection)

    override val globalMessageError: String = getString(R.string.globalMessageError)

    override val emptyDataMessage: String = getString(R.string.field_cont_be_empty)

    override val enterValidEmailAddress: String =
        getString(R.string.enter_a_valid_email_address)

    override val invalidEmailOrPassword: String = getString(R.string.invalid_email_or_password)

    override val failedEmailWhenEmpty: String =
        getString(R.string.the_email_shouldn_t_be_empty)

    override val failedFullNameWhenEmpty: String =
        getString(R.string.the_full_name_shouldn_t_be_empty)

    override val invalidUsername: String = getString(R.string.invalid_username)
    override val invalidEmail: String = getString(R.string.invalid_email)
    override val passwordMismatch: String = getString(R.string.password_mismatch)
    override val invalidLocation: String = getString(R.string.invalid_location)
    override val invalidPhoneNumber: String = getString(R.string.invalid_phone_number)
    override val invalidPassword: String = getString(R.string.invalid_password)
    override val invalidBestBarterSpot: String = getString(R.string.invalid_best_barter_spot)
    override val invalidTitle: String = getString(R.string.invalid_title)
    override val invalidPlace: String = getString(R.string.invalid_place)
    override val invalidDetails: String = getString(R.string.invalid_details)
    override val emptyImageMessage: String = getString(R.string.empty_image)


    private fun getString(@androidx.annotation.StringRes stringsRes: Int): String {
        return context.getString(stringsRes)
    }
}