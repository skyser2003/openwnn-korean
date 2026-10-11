import importlib.util
import unittest
from datetime import date
from pathlib import Path


class ReleaseVersionTest(unittest.TestCase):
    def resolve(self, **overrides):
        path = Path(__file__).resolve().parents[1] / "release_version.py"
        self.assertTrue(path.is_file(), "The release version resolver is missing")
        spec = importlib.util.spec_from_file_location("release_version", path)
        module = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(module)
        arguments = dict(base_code=25090801, run_number=7, today=date(2026, 10, 11))
        arguments.update(overrides)
        return module.resolve_version(**arguments)

    def test_automatic_version_uses_date_and_run_number(self):
        self.assertEqual(
            self.resolve(),
            {"version": "2026.10.11.7", "tag": "v2026.10.11.7", "code": 25090808},
        )

    def test_explicit_version_sets_both_apk_version_and_tag(self):
        self.assertEqual(
            self.resolve(explicit_version="v1.2.3-rc.1"),
            {"version": "1.2.3-rc.1", "tag": "v1.2.3-rc.1", "code": 25090808},
        )

    def test_version_code_increases_even_with_an_explicit_version(self):
        earlier = self.resolve(explicit_version="1.2.3")
        later = self.resolve(explicit_version="1.2.4", run_number=8)
        self.assertGreater(later["code"], earlier["code"])

    def test_versions_cannot_inject_commands_or_extra_github_outputs(self):
        for version in ("1.2.3\ntag=other", "$(touch /tmp/invalid)", "../1.2", "1..2", " latest "):
            with self.subTest(version=version), self.assertRaises(ValueError):
                self.resolve(explicit_version=version)

    def test_invalid_android_version_codes_are_rejected(self):
        for base, run in ((0, 7), (25090801, 0), (2100000000, 1)):
            with self.subTest(base=base, run=run), self.assertRaises(ValueError):
                self.resolve(base_code=base, run_number=run)


if __name__ == "__main__":
    unittest.main()
