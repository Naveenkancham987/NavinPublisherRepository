package com.pk.publisher.utility;

/*
 * PublisherServiceConstants class
 */
public class PublisherServiceConstants {
  private PublisherServiceConstants() {
    throw new IllegalStateException("Utility class");
  }

  public static final String NUMBER_MASK = ".([a-zA-Z0-9]{3})$";
  public static final String DATE_MASK = "(?!=^)[0-9](?=.{4,}$)";
  public static final String EMAIL_MASK = "(^[a-zA-Z0-9]{3}).(?=.*@)";
  public static final String DATE_PATTERN = "*";
  public static final String SUCCESSFULLY_PUBLISHED = " Customer Object Is Successfully Published ";
  public static final String TOPIC = "PublishedTopic";
  public static final String USER_NAME = "navi";
  public static final String PASSWORD = "navi";
  public static final String USER = "USER";
  public static final String PATTERN = "****";
  public static final String SUCCESS = "SUCCESS";
  public static final String ERROR = "ERROR";



}
