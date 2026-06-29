# Spring AI integration

This project now includes Spring AI (1.1.x). It adds one AI-powered GraphQL
query, `recommendBooks(topic)`, which asks a language model for book suggestions.

## What was added

- pom.xml: the Spring AI BOM + the `spring-ai-starter-model-openai` starter
- AiController.java: builds a ChatClient and exposes the GraphQL query
- schema.graphqls: `recommendBooks(topic: String!): String`
- application.properties: AI config with safe defaults

## Important: it builds without a key, but needs one to actually answer

Spring AI talks to an external model; it doesn't ship one. The config defaults
the API key to "not-set", so the app still compiles, passes tests, and boots
with no key (CI and Render stay green). The AI query only works once you provide
a real key via the OPENAI_API_KEY environment variable.

## Getting a key (free and paid options)

- OpenAI (paid, but cheap): platform.openai.com -> API keys. gpt-4o-mini costs
  fractions of a cent per call. Requires adding billing.
- Groq (free, no card): console.groq.com -> create an API key. It's
  OpenAI-compatible, so it works with the same starter by overriding the
  base URL and model (see below).
- Google Gemini (free tier): aistudio.google.com for an API key (would use the
  google-genai starter instead; ask if you want that wired up).

## Run it with OpenAI

Set the env var, then start the app:

    # macOS/Linux
    export OPENAI_API_KEY=sk-...your-key...
    mvn spring-boot:run

    # Windows PowerShell
    $env:OPENAI_API_KEY="sk-...your-key..."
    mvn spring-boot:run

## Run it free with Groq (OpenAI-compatible)

    export OPENAI_API_KEY=gsk_...your-groq-key...
    export AI_BASE_URL=https://api.groq.com/openai
    export AI_MODEL=llama-3.3-70b-versatile
    mvn spring-boot:run

(Check console.groq.com for the current free model names.)

## Try the query

Open http://localhost:8080/graphiql and run:

    query {
      recommendBooks(topic: "space exploration")
    }

You'll get a few AI-generated recommendations back as text.

## Deploying on Render

Add the key as an environment variable in the Render dashboard:
Settings -> Environment -> Add Environment Variable
  Key:   OPENAI_API_KEY
  Value: your key
(and AI_BASE_URL / AI_MODEL too if using Groq). Redeploy, and the live app's
recommendBooks query works.

## Note

Without a valid key, calling recommendBooks returns an authentication error from
the provider - that is expected, not a bug. Everything else (books queries,
mutations, security, H2) works regardless.
