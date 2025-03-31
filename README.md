# Booking Appointment System

## Introduction  
Users can book, manage, and receive notifications about their appointments.

## Description  
Users can book, manage, and receive notifications about their appointments.

## User Requirements  

### Actors  
**Patient** – Can book, cancel, or update appointments.

### Functional Requirements  
- Patients can book appointments based on available time slots.  
- Users can cancel or reschedule appointments.  

### Non-Functional Requirements  
- The scheduling system prevents overlapping appointments.  
- Changes are notified in real-time.  
 



## Design Patterns Used  

### Creational Patterns  
1. **Factory Method Pattern** – Creation of the appointments with different types.  
2. **Singleton Pattern** – Ensures only one instance of scheduling exists.  
3. **Builder Pattern** – Creates complex appointment objects.  

### Structural Patterns  
1. **Facade Pattern** – Provides a simplified interface for users to book appointments.  
2. **Adapter Pattern** – Synchronizes with external calendars.  
3. **Proxy Pattern** – Controls access to scheduling functionalities.  

### Behavioral Patterns  
1. **Observer Pattern** – Notifies when appointments are created or canceled.  
2. **Command Pattern** – Encapsulates booking, canceling, and modifying into commands.  
3. **Strategy Pattern** – Implements two appointment scheduling strategies: first-come-first-serve and priority-based.  

## Dependencies  
- List any prerequisites, libraries, OS version, etc., needed before installing the program.  
  - Example: Windows 10  

## Installing  
- How and where to download your program.  
- Any modifications needed to be made to files/folders.  

## Executing Program  
- How to run the program.  
- Step-by-step instructions:  

```sh
# Example command to start the program
some-command-to-run
