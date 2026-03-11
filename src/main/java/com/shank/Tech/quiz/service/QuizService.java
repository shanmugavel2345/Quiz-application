package com.shank.Tech.quiz.service;

import com.shank.Tech.quiz.entity.QuizState;
import com.shank.Tech.quiz.model.Question;
import com.shank.Tech.quiz.repository.QuizStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {
    
    private final QuizStateRepository quizStateRepository;

    private final List<Question> questionBank = initializeQuestionBank();

    public List<Question> getRandomQuestions(int count) {
        List<Question> shuffledQuestions = shuffleQuestions(questionBank);
        int finalCount = Math.min(Math.min(30, count), shuffledQuestions.size());
        List<Question> selectedQuestions = new ArrayList<>(shuffledQuestions.subList(0, finalCount));

        List<Question> randomized = new ArrayList<>();
        for (Question question : selectedQuestions) {
            List<String> shuffledOptions = shuffleOptions(question.getOptions());
            randomized.add(new Question(question.getEmoji(), shuffledOptions, question.getCorrectAnswer()));
        }
        return randomized;
    }

    public List<Question> shuffleQuestions(List<Question> questions) {
        List<Question> copy = new ArrayList<>(questions);
        Collections.shuffle(copy);
        return copy;
    }

    public List<String> shuffleOptions(List<String> options) {
        List<String> copy = new ArrayList<>(options);
        Collections.shuffle(copy);
        return copy;
    }
    
    @Transactional
    public void startQuiz() {
        QuizState quizState = getOrCreateQuizState();
        quizState.setStarted(true);
        quizState.setEnded(false);
        quizStateRepository.save(quizState);
    }
    
    public boolean isQuizStarted() {
        QuizState quizState = getOrCreateQuizState();
        return quizState.isStarted();
    }

    public boolean isQuizEnded() {
        QuizState quizState = getOrCreateQuizState();
        return quizState.isEnded();
    }

    @Transactional
    public void endQuiz() {
        QuizState quizState = getOrCreateQuizState();
        quizState.setStarted(false);
        quizState.setEnded(true);
        quizStateRepository.save(quizState);
    }
    
    @Transactional
    public void resetQuiz() {
        QuizState quizState = getOrCreateQuizState();
        quizState.setStarted(false);
        quizState.setEnded(false);
        quizStateRepository.save(quizState);
    }
    
    private QuizState getOrCreateQuizState() {
        Optional<QuizState> quizStateOpt = quizStateRepository.findById(1L);
        if (quizStateOpt.isPresent()) {
            return quizStateOpt.get();
        } else {
            QuizState newState = new QuizState();
            newState.setId(1L);
            newState.setStarted(false);
            newState.setEnded(false);
            return quizStateRepository.save(newState);
        }
    }

    private List<Question> initializeQuestionBank() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question("☁️ + 🖥️", List.of("Cloud Server", "Data Center", "Hosting", "Storage"), "Cloud Server"));
        questions.add(new Question("💻 + 🔍", List.of("Debugging", "Coding", "Searching", "Programming"), "Debugging"));
        questions.add(new Question("📦 + ⚙️", List.of("Software Package", "Deployment", "Install", "Build"), "Software Package"));
        questions.add(new Question("🧑‍💻 + 🐞", List.of("Bug Fixing", "Coding", "Debugging", "Testing"), "Bug Fixing"));
        questions.add(new Question("🔐 + 💾", List.of("Secure Storage", "Encryption", "Backup", "Password"), "Secure Storage"));
        questions.add(new Question("📊 + 🖥️", List.of("Data Visualization", "Programming", "Networking", "Security"), "Data Visualization"));
        questions.add(new Question("🌐 + 🏠", List.of("Homepage", "Website", "Domain", "Internet"), "Homepage"));
        questions.add(new Question("📡 + 💻", List.of("Network Connection", "WiFi", "Router", "Switch"), "Network Connection"));
        questions.add(new Question("🔄 + 💻", List.of("System Restart", "Refresh", "Update", "Install"), "System Restart"));
        questions.add(new Question("📁 + 🔐", List.of("Secure Folder", "Backup", "Encryption", "Password"), "Secure Folder"));
        questions.add(new Question("💻 + ☁️ + 🔗", List.of("Cloud Integration", "Hosting", "Cloud Storage", "Network"), "Cloud Integration"));
        questions.add(new Question("🧠 + 📊", List.of("Data Intelligence", "Analytics", "Machine Learning", "AI"), "Data Intelligence"));
        questions.add(new Question("📡 + 📶 + 💻", List.of("Wireless Network", "Internet", "Router", "Server"), "Wireless Network"));
        questions.add(new Question("💾 + 🔄", List.of("Data Backup", "Update", "Sync", "Storage"), "Data Backup"));
        questions.add(new Question("💻 + 📤", List.of("Upload", "Download", "Install", "Deploy"), "Upload"));
        questions.add(new Question("💻 + 📥", List.of("Download", "Upload", "Backup", "Install"), "Download"));
        questions.add(new Question("📦 + 💻 + 🚀", List.of("Application Deployment", "Software Install", "Package Update", "Hosting"), "Application Deployment"));
        questions.add(new Question("🔐 + 🌍", List.of("Internet Security", "VPN", "Firewall", "HTTPS"), "Internet Security"));
        questions.add(new Question("📊 + 📈 + 💻", List.of("Data Analysis", "Programming", "Security", "AI"), "Data Analysis"));
        questions.add(new Question("💻 + 📂", List.of("File Management", "Storage", "Database", "Backup"), "File Management"));
        questions.add(new Question("📡 + 🖥️ + 🌐", List.of("Internet Server", "Router", "Hosting", "Network"), "Internet Server"));
        questions.add(new Question("🧠 + 🤖 + 💻", List.of("Artificial Intelligence System", "Automation", "Robotics", "Machine"), "Artificial Intelligence System"));
        questions.add(new Question("📊 + 📁", List.of("Data Storage", "Database", "Backup", "Cloud"), "Data Storage"));
        questions.add(new Question("🔑 + 💻", List.of("Authentication", "Encryption", "Password", "Login"), "Authentication"));
        questions.add(new Question("📡 + 🔄", List.of("Network Refresh", "Restart", "Router Reset", "Update"), "Network Refresh"));
        questions.add(new Question("💻 + 🛠️", List.of("Software Development", "Coding", "Debugging", "Programming"), "Software Development"));
        questions.add(new Question("🖥️ + 🧑‍💻", List.of("Developer Workstation", "Programmer", "Coding", "Server"), "Developer Workstation"));
        questions.add(new Question("📂 + ☁️", List.of("Cloud Files", "Backup", "Storage", "Server"), "Cloud Files"));
        questions.add(new Question("🔐 + 📡", List.of("Secure Network", "VPN", "Firewall", "Router"), "Secure Network"));
        questions.add(new Question("💻 + 🌐 + ⚙️", List.of("Web Application", "Website", "Server", "Hosting"), "Web Application"));
        questions.add(new Question("🐙 + 🐱", List.of("GitHub", "GitLab", "Bitbucket", "Jenkins"), "GitHub"));
        questions.add(new Question("🐳 + 📦", List.of("Docker", "Kubernetes", "Podman", "Container"), "Docker"));
        questions.add(new Question("☁️ + ⚙️", List.of("DevOps", "Networking", "Security", "Hosting"), "DevOps"));
        questions.add(new Question("🐍 + 💻", List.of("Python Programming", "Java Programming", "C++", "Ruby"), "Python Programming"));
        questions.add(new Question("📊 + 🤖", List.of("Machine Learning", "Data Entry", "Networking", "Database"), "Machine Learning"));
        questions.add(new Question("🧱 + 🔥", List.of("Firewall", "Antivirus", "VPN", "Router"), "Firewall"));
        questions.add(new Question("📧 + 🎣", List.of("Phishing", "Spam", "Email", "Malware"), "Phishing"));
        questions.add(new Question("🔑 + 🔒", List.of("Encryption", "Login", "Password", "Security"), "Encryption"));
        questions.add(new Question("🌐 + 📄", List.of("Website", "Browser", "Domain", "Server"), "Website"));
        questions.add(new Question("📡 + 📍", List.of("GPS", "WiFi", "Bluetooth", "NFC"), "GPS"));
        questions.add(new Question("💻 + 🧠", List.of("CPU", "GPU", "RAM", "SSD"), "CPU"));
        questions.add(new Question("⚡ + 💾", List.of("RAM", "Hard Disk", "SSD", "Storage"), "RAM"));
        questions.add(new Question("📜 + 🌐", List.of("JavaScript", "HTML", "CSS", "PHP"), "JavaScript"));
        questions.add(new Question("🧑‍💻 + ☕", List.of("Java Developer", "Programmer", "Tester", "Designer"), "Java Developer"));
        questions.add(new Question("☁️ + 💾", List.of("Cloud Storage", "Database", "Server", "Backup"), "Cloud Storage"));
        questions.add(new Question("📊 + 📈", List.of("Data Analytics", "AI", "Programming", "Networking"), "Data Analytics"));
        questions.add(new Question("🤖 + 🧠", List.of("Artificial Intelligence", "Automation", "Robotics", "Machine"), "Artificial Intelligence"));
        questions.add(new Question("📱 + 🔐", List.of("Two Factor Authentication", "Password", "Encryption", "Login"), "Two Factor Authentication"));
        questions.add(new Question("📡 + 🌐", List.of("Internet Connection", "Router", "Network", "Server"), "Internet Connection"));
        questions.add(new Question("📁 + 🔍", List.of("File Search", "Database", "Folder", "Server"), "File Search"));
        questions.add(new Question("🐳 + ☸️", List.of("Kubernetes", "Docker", "Jenkins", "DevOps"), "Kubernetes"));
        questions.add(new Question("🔄 + 🌐", List.of("Refresh Page", "Restart Server", "Reload Internet", "Update"), "Refresh Page"));
        questions.add(new Question("📦 + 🚀", List.of("Deployment", "Download", "Upload", "Install"), "Deployment"));
        questions.add(new Question("🌐 + 🔗", List.of("URL", "DNS", "Domain", "Website"), "URL"));
        questions.add(new Question("💻 + 📶", List.of("WiFi", "Bluetooth", "VPN", "Router"), "WiFi"));
        questions.add(new Question("🦠 + 💻", List.of("Malware", "Antivirus", "Firewall", "Spyware"), "Malware"));
        questions.add(new Question("🛡️ + 💻", List.of("Antivirus", "Firewall", "VPN", "Proxy"), "Antivirus"));
        questions.add(new Question("📊 + 🧠 + 🤖", List.of("Machine Learning", "AI", "Analytics", "Automation"), "Machine Learning"));
        questions.add(new Question("☁️ + 📡 + 🌐", List.of("Cloud Network", "Server", "Hosting", "Database"), "Cloud Network"));
        questions.add(new Question("💻 + 🌐 + 📄", List.of("Web Development", "Programming", "Networking", "Server"), "Web Development"));

        return questions;
    }
}
