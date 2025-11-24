// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    // Added manually on Nov-24, 2025
    id("com.google.gms.google-services") version "4.4.4" apply false
}