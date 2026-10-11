"""Resolve the APK version and tag for one GitHub Actions run."""

import argparse
import re
from datetime import datetime, timezone
from pathlib import Path


def resolve_version(base_code, run_number, explicit_version="", today=None):
    if base_code < 1 or run_number < 1 or base_code + run_number > 2100000000:
        raise ValueError("The generated Android versionCode must be between 1 and 2100000000")
    today = today or datetime.now(timezone.utc).date()
    version = explicit_version.removeprefix("v") if explicit_version else f"{today:%Y.%m.%d}.{run_number}"
    if not re.fullmatch(r"[0-9]+(?:[.-][A-Za-z0-9]+)*", version, flags=re.ASCII):
        raise ValueError("Version must start with a number and contain only letters, numbers, dots, and hyphens")
    return {"version": version, "tag": f"v{version}", "code": base_code + run_number}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--base-code", type=int, required=True)
    parser.add_argument("--run-number", type=int, required=True)
    parser.add_argument("--version", default="")
    parser.add_argument("--output", type=Path, required=True)
    args = parser.parse_args()
    try:
        values = resolve_version(args.base_code, args.run_number, args.version)
    except ValueError as error:
        parser.error(str(error))
    with args.output.open("a", encoding="utf-8") as output:
        for key, value in values.items():
            output.write(f"{key}={value}\n")


if __name__ == "__main__":
    main()
