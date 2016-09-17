# (Google) HttpTransport Jersey

A Google Http client HttpTransport implementation backed by Jersey

## Overview

Google Http client is an abstract frontend allowing for simple substitution
of an Http backend while providing a simple, concise, and testable API for 
consumers.

While Google Http client provides multiple HttpTransport implementations,
it does not provide one backed by Jersey. This library aims to fix that.
