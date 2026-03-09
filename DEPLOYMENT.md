# Tech Quiz Application - AWS Amplify Deployment Guide

## 🚀 Deploy to AWS Amplify

### Prerequisites
- AWS Account
- GitHub repository: https://github.com/shanmugavel2345/Quiz-application
- AWS Amplify Console access

### Step-by-Step Deployment Instructions

#### 1. **Access AWS Amplify Console**
   - Go to [AWS Amplify Console](https://console.aws.amazon.com/amplify/)
   - Sign in to your AWS account

#### 2. **Create New App**
   - Click "New app" → "Host web app"
   - Choose "GitHub" as your source code provider
   - Authorize AWS Amplify to access your GitHub account

#### 3. **Select Repository**
   - Choose repository: `shanmugavel2345/Quiz-application`
   - Select branch: `main`
   - Click "Next"

#### 4. **Configure Build Settings**
   - App name: `tech-quiz-application`
   - Environment: `production`
   - The build settings should auto-detect the `amplify.yml` file
   - If not detected, use the build specification below

#### 5. **Build Specification (amplify.yml)**
```yaml
version: 1
frontend:
  phases:
    preBuild:
      commands:
        - echo "Installing dependencies..."
        - npm install -g http-server
    build:
      commands:
        - echo "Building the application..."
        - mkdir -p build
        - cp -r src/main/resources/static/* build/
        - echo "Build completed successfully"
    postBuild:
      commands:
        - echo "Post-build phase completed"
  artifacts:
    baseDirectory: build
    files:
      - '**/*'
```

#### 6. **Deploy**
   - Review and click "Save and deploy"
   - Wait for the build process to complete (~3-5 minutes)

#### 7. **Access Your Live Application**
   - Once deployed, you'll get a URL like: `https://main.xxxxxxx.amplifyapp.com`
   - Your Tech Quiz application will be live and accessible globally

### 🎯 Features Available After Deployment
- ✅ Interactive Tech Emoji Quiz
- ✅ Skip Timer Button (⏭️ SKIP)
- ✅ Team Management System
- ✅ Real-time Scoring
- ✅ Responsive Design
- ✅ Automatic HTTPS
- ✅ Global CDN Distribution

### 🔧 Post-Deployment Configuration
- **Custom Domain:** You can add your own domain in Amplify console
- **Environment Variables:** Configure if needed in Amplify settings
- **Monitoring:** Use AWS CloudWatch for performance monitoring

### 📝 Auto-Deployment
- Any changes pushed to the `main` branch will trigger automatic rebuilds
- Deployment typically takes 3-5 minutes

### 🛠️ Troubleshooting
- Check build logs in Amplify console if deployment fails
- Ensure all static files are in `src/main/resources/static/`
- Verify `amplify.yml` is in the root directory

### 📞 Support
For deployment issues, check the Amplify build logs or AWS documentation.