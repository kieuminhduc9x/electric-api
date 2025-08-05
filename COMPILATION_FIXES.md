# Compilation Fixes Summary

## Enum Values Found:

### AlertLevel.Severity:
- Correct: `LOW, MEDIUM, HIGH, CRITICAL`
- Wrong: `INFO, WARNING, ERROR` 

### Alert.AlertType:
- Correct: `OVER_VOLTAGE, OVER_CURRENT, OVER_TEMPERATURE, UNDER_VOLTAGE, UNDER_CURRENT, DEVICE_OFFLINE, DEVICE_ERROR, MAINTENANCE_DUE`
- Wrong: `VOLTAGE, CURRENT, TEMPERATURE, COMMUNICATION`

### SystemConfig.DataType:
- Correct: `STRING, INTEGER, DECIMAL, BOOLEAN, JSON`
- Wrong: `NUMBER`

## Data Type Issues:
- Alert.thresholdValue and actualValue are **BigDecimal**, not String
- Need to convert String to BigDecimal using `new BigDecimal(stringValue)`

## Files to Fix:
1. DataSeeder.java - AlertLevel severity values, SystemConfig data types, Alert threshold/actual values
2. AlertController.java - AlertType values, BigDecimal conversion
3. FakeDataService.java - BigDecimal conversion

## Quick Fixes:
- Replace `AlertLevel.Severity.INFO` → `AlertLevel.Severity.LOW`
- Replace `AlertLevel.Severity.WARNING` → `AlertLevel.Severity.MEDIUM`  
- Replace `AlertLevel.Severity.ERROR` → `AlertLevel.Severity.HIGH`
- Replace `Alert.AlertType.VOLTAGE` → `Alert.AlertType.OVER_VOLTAGE`
- Replace `Alert.AlertType.CURRENT` → `Alert.AlertType.OVER_CURRENT`
- Replace `Alert.AlertType.TEMPERATURE` → `Alert.AlertType.OVER_TEMPERATURE`
- Replace `Alert.AlertType.COMMUNICATION` → `Alert.AlertType.DEVICE_OFFLINE`
- Replace `SystemConfig.DataType.NUMBER` → `SystemConfig.DataType.INTEGER`
- Replace `setThresholdValue(String.valueOf(...))` → `setThresholdValue(new BigDecimal(...))` 