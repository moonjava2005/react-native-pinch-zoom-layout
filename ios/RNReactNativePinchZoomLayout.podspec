
Pod::Spec.new do |s|
  s.name         = "RNPinchZoomLayout"
  s.version      = "1.0.0"
  s.summary      = "RNPinchZoomLayout"
  s.description  = <<-DESC
                  RNPinchZoomLayout
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "moonjava@gmail.com" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/moonjava2005/react-native-pinch-zoom-layout.git", :tag => "master" }
  s.source_files  = "RNPinchZoomLayout/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  