# APK releases

Every push to `release` runs the Android unit tests, builds a release APK, verifies
its signature, and publishes it with a SHA-256 checksum in GitHub Releases. The
same files are also saved as a workflow artifact for 30 days. Tags point to the
commit that was built. Builds use Java 17, Android platform 36, and build-tools
35.0.0.

## Signing setup

Before publishing, add these **repository secrets** under Settings → Secrets and
variables → Actions:

| Secret | Value |
| --- | --- |
| `ANDROID_KEYSTORE_BASE64` | Base64-encoded release keystore |
| `ANDROID_KEYSTORE_PASSWORD` | Keystore password |
| `ANDROID_KEY_ALIAS` | Signing key alias |
| `ANDROID_KEY_PASSWORD` | Signing key password |

Keep a private backup of the keystore and passwords. Use the same key for every
release so Android can update installed copies. A new key cannot update an APK
signed with the upstream author's key or a different debug key.

With an authenticated GitHub CLI, set a keystore secret without putting it in a
command argument:

```sh
base64 < release.jks | gh secret set ANDROID_KEYSTORE_BASE64 --repo skyser2003/openwnn-korean
gh secret set ANDROID_KEYSTORE_PASSWORD --repo skyser2003/openwnn-korean
gh secret set ANDROID_KEY_ALIAS --repo skyser2003/openwnn-korean
gh secret set ANDROID_KEY_PASSWORD --repo skyser2003/openwnn-korean
```

The last three commands prompt for their values. Missing secrets fail the
workflow before publishing; it never publishes an unsigned APK.

## Automatic and explicit versions

Push changes to the `release` branch:

```sh
git push origin release
```

The automatic APK version is `YYYY.MM.DD.RUN`, using the UTC build date and
GitHub's workflow run number. For example, version `2026.10.11.7` creates tag
`v2026.10.11.7`. The Android version code is the checked-in base version code plus
the workflow run number. Keep that base code stable or increase it; do not lower
it or delete and recreate the workflow's run history. Rerunning a failed run
keeps its run number.

To specify a version, open **Actions → Release APK → Run workflow**, select the
`release` branch, and enter a version such as `1.2.3` or `v1.2.3-rc.1`. The APK
version and tag both use that value, and the version code still increases. An
empty version uses the automatic format. You can also dispatch from the CLI:

```sh
gh workflow run release.yml --ref release --field version=1.2.3
```

Manual runs on other branches are skipped. Existing tags are rejected rather
than overwritten. If a tag has already been published, choose a new version.
The workflow file must also exist on the default branch for manual dispatch to
be available. Each push runs independently and uses its own workflow run number.

## Local verification

```sh
python3 -m unittest discover -s scripts/tests -v
./gradlew :app:testDebugUnitTest :app:assembleRelease
```

For a locally signed release, set `ANDROID_KEYSTORE_PATH`,
`ANDROID_KEYSTORE_PASSWORD`, `ANDROID_KEY_ALIAS`, and `ANDROID_KEY_PASSWORD` in
your environment, then build with optional version overrides:

```sh
./gradlew :app:assembleRelease -PreleaseVersionName=1.2.3 -PreleaseVersionCode=25090802
```

Without the signing environment variables, local release builds remain unsigned.
The CI workflow always requires signing and verifies the resulting APK before
publishing.
